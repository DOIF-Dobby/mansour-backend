package org.mj.mansour.auth.service

import org.mj.mansour.auth.client.UserServiceClient
import org.mj.mansour.auth.jwt.JwtTokenProvider
import org.mj.mansour.auth.oauth2.OAuth2TokenExchanger
import org.mj.mansour.auth.oauth2.OAuth2UserInfo
import org.mj.mansour.auth.oauth2.OAuth2UserInfoFetcher
import org.mj.mansour.auth.oauth2.exception.OAuth2AuthenticationException
import org.mj.mansour.auth.oauth2.findClientRegistration
import org.mj.mansour.contract.user.FindOrCreateUser
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val oAuth2TokenExchanger: OAuth2TokenExchanger,
    private val oAuth2UserInfoFetcher: OAuth2UserInfoFetcher,
    private val userServiceClient: UserServiceClient,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    /**
     * OAuth2 인증 하고 사용자를 찾거나 생성한 후, JWT 토큰을 발급합니다.
     */
    fun loginWithOAuth2(provider: String, authorizationCode: String): String {
        val clientRegistration = findClientRegistration(clientRegistrationRepository, provider)
        val accessToken = oAuth2TokenExchanger.exchange(clientRegistration, provider, authorizationCode)
        val attributes = oAuth2UserInfoFetcher.fetch(clientRegistration, accessToken)

        val userInfo = OAuth2UserInfo.from(provider, attributes)

        val request = FindOrCreateUser(
            email = userInfo.email,
            username = userInfo.username,
            provider = userInfo.provider,
            providerId = userInfo.providerId,
        )

        val userResponse = userServiceClient.findOrCreateUser(request).data ?: throw OAuth2AuthenticationException()
        return jwtTokenProvider.generateToken(
            subject = userResponse.userId.toString(),
            claims = mapOf(
                "email" to userResponse.email,
                "username" to userResponse.username,
                "authentications" to userResponse.authentications
            )
        )
    }
}

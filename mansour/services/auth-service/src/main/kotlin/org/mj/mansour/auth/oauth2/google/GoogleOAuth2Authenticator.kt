package org.mj.mansour.auth.oauth2.google

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.mansour.shared.domain.AuthProvider
import org.mj.mansour.auth.oauth2.OAuth2Authentication
import org.mj.mansour.auth.oauth2.OAuth2AuthenticationException
import org.mj.mansour.auth.oauth2.OAuth2Authenticator
import org.mj.mansour.auth.oauth2.OAuth2GrantType
import org.mj.mansour.auth.oauth2.OAuth2ParameterNames
import org.mj.mansour.auth.oauth2.OAuth2Properties
import org.mj.mansour.system.core.security.JwtUtils
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient

@Component
class GoogleOAuth2Authenticator(
    oAuth2Properties: OAuth2Properties,
    private val restClient: RestClient,
    private val objectMapper: ObjectMapper,
) : OAuth2Authenticator {
    private val googleProvider = oAuth2Properties.provider[AuthProvider.GOOGLE.name]
        ?: throw IllegalStateException("Google OAuth2 properties not configured")

    override fun authenticate(authorizationCode: String, redirectUri: String): OAuth2Authentication {
        // grant_type = authorization_code 일 때 access token 요청을 위한 파라미터
        val tokenRequest = LinkedMultiValueMap<String, String>().apply {
            add(OAuth2ParameterNames.GRANT_TYPE, OAuth2GrantType.AUTHORIZATION_CODE.value)
            add(OAuth2ParameterNames.CODE, authorizationCode)
            add(OAuth2ParameterNames.REDIRECT_URI, redirectUri)
            add(OAuth2ParameterNames.CLIENT_ID, googleProvider.clientId)
            add(OAuth2ParameterNames.CLIENT_SECRET, googleProvider.clientSecret)
        }

        // 요청 보내기
        val response = restClient.post()
            .uri(TOKEN_ENDPOINT)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(tokenRequest)
            .retrieve()
            .toEntity(GoogleOAuth2AccessTokenResponse::class.java)

        // 응답 처리
        val tokenResponse = response.body ?: throw OAuth2AuthenticationException()
        val payload = JwtUtils.decodePayload(tokenResponse.idToken)
        val payloadMap = objectMapper.readValue(payload, object : TypeReference<Map<String, Any>>() {})

        return GoogleOAuth2Authentication(
            subject = payloadMap["sub"] as String,
            email = payloadMap["email"] as String,
            name = payloadMap["name"] as String,
        )

    }

    override fun supports(providerType: AuthProvider): Boolean {
        return AuthProvider.GOOGLE == providerType
    }

    companion object {
        private const val TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token"
    }
}

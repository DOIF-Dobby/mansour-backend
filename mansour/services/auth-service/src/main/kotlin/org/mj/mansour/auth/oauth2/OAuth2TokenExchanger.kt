package org.mj.mansour.auth.oauth2

import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse
import org.springframework.stereotype.Component

@Component
class OAuth2TokenExchanger {

    private val tokenResponseClient = RestClientAuthorizationCodeTokenResponseClient()

    /**
     * 주어진 OAuth2 제공자와 인증 코드를 사용하여 액세스 토큰을 교환합니다.
     */
    fun exchange(clientRegistration: ClientRegistration, authorizationCode: String): OAuth2AccessToken {
        val authorizationRequest = OAuth2AuthorizationRequest
            .authorizationCode()
            .clientId(clientRegistration.clientId)
            .authorizationUri(clientRegistration.providerDetails.authorizationUri)
            .redirectUri(clientRegistration.redirectUri)
            .scopes(clientRegistration.scopes)
            .build()

        val authorizationResponse = OAuth2AuthorizationResponse
            .success(authorizationCode)
            .redirectUri(authorizationRequest.redirectUri)
            .build()

        val authorizationExchange = OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse)

        val codeGrantRequest = OAuth2AuthorizationCodeGrantRequest(clientRegistration, authorizationExchange)
        val tokenResponse = tokenResponseClient.getTokenResponse(codeGrantRequest)

        return tokenResponse.accessToken
    }
}

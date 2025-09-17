package org.mj.mansour.auth.oauth2

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2RedirectUrlResolver(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {

    /**
     * 주어진 OAuth2 제공자에 대한 리다이렉트 URL을 생성합니다.
     *
     * @param provider OAuth2 제공자의 이름 (예: "google", "facebook")
     * @return 생성된 리다이렉트 URL
     * @throws org.mj.mansour.auth.oauth2.exception.OAuth2UnsupportedProviderException 지원되지 않는 제공자일 경우
     */
    fun resolveRedirectUrl(provider: String): String {
        val clientRegistration = findClientRegistration(clientRegistrationRepository, provider)

        return UriComponentsBuilder
            .fromUriString(clientRegistration.providerDetails.authorizationUri)
            .queryParam("client_id", clientRegistration.clientId)
            .queryParam("redirect_uri", clientRegistration.redirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", clientRegistration.scopes.joinToString(" "))
            .build()
            .encode()
            .toUriString()
    }
}

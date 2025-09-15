package org.mj.mansour.auth.oauth2.google

import org.mansour.shared.domain.AuthProvider
import org.mj.mansour.auth.oauth2.OAuth2Properties
import org.mj.mansour.auth.oauth2.OAuth2RedirectUriResolver
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class GoogleOAuth2RedirectUriResolver(
    oAuth2Properties: OAuth2Properties
) : OAuth2RedirectUriResolver {

    private val googleProvider = oAuth2Properties.provider[AuthProvider.GOOGLE.name]
        ?: throw IllegalStateException("Google OAuth2 properties not configured")


    override fun resolveRedirectUri(redirectUri: String): String {
        return UriComponentsBuilder.fromUriString(GOOGLE_AUTHORIZATION_URL)
            .queryParam("client_id", googleProvider.clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", googleProvider.scope.joinToString(separator = " "))
            .build()
            .encode()
            .toUriString()

    }

    override fun supports(providerType: AuthProvider): Boolean {
        return AuthProvider.GOOGLE == providerType
    }

    companion object {
        private const val GOOGLE_AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth"
    }
}

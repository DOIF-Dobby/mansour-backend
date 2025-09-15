package org.mj.mansour.auth.oauth2

import org.mansour.shared.domain.AuthProvider

interface OAuth2RedirectUriResolver {

    fun resolveRedirectUri(redirectUri: String): String

    fun supports(providerType: AuthProvider): Boolean

}

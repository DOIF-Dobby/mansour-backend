package org.mj.mansour.auth.oauth2

import org.mansour.shared.domain.AuthProvider

interface OAuth2Authenticator {

    fun authenticate(authorizationCode: String, redirectUri: String): OAuth2Authentication

    fun supports(providerType: AuthProvider): Boolean

}

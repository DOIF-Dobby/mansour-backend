package org.mj.mansour.auth.oauth2.google

import org.mansour.shared.domain.AuthProvider
import org.mj.mansour.auth.oauth2.OAuth2Authentication

data class GoogleOAuth2Authentication(
    override val subject: String,
    override val email: String,
    override val name: String,
) : OAuth2Authentication {
    override val providerType: AuthProvider = AuthProvider.GOOGLE
}

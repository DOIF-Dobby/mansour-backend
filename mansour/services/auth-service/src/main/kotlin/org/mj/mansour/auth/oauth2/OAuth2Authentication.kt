package org.mj.mansour.auth.oauth2

import org.mansour.shared.domain.AuthProvider

interface OAuth2Authentication {
    val subject: String
    val email: String
    val name: String
    val providerType: AuthProvider
}


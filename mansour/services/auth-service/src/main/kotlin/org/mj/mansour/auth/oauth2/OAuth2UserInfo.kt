package org.mj.mansour.auth.oauth2

import org.mansour.shared.domain.enums.AuthProvider

data class OAuth2UserInfo(
    val provider: AuthProvider,
    val providerId: String,
    val email: String,
    val username: String
) {

    companion object {
        fun from(provider: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return when (AuthProvider.valueOf(provider.uppercase())) {
                AuthProvider.GOOGLE -> google(attributes)
            }
        }

        private fun google(attributes: Map<String, Any>): OAuth2UserInfo {
            return OAuth2UserInfo(
                provider = AuthProvider.GOOGLE,
                providerId = attributes["sub"] as String,
                email = attributes["email"] as String,
                username = attributes["name"] as String
            )
        }
    }
}

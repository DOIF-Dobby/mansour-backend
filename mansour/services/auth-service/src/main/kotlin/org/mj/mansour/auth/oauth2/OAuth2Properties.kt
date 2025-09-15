package org.mj.mansour.auth.oauth2

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth2")
data class OAuth2Properties(
    val provider: Map<String, Provider> = emptyMap()
) {
    data class Provider(
        val clientId: String,
        val clientSecret: String,
        val scope: List<String> = emptyList()
    )

}

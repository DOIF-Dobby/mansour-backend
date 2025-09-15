package org.mj.mansour.infra.gateway.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt.access-token")
data class JwtTokenProperties(
    val publicKeyPath: String,
)

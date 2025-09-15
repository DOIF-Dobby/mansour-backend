package org.mj.mansour.auth.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt.access-token")
data class JwtTokenProperties(
    val privateKeyPath: String,
    var expiration: Duration = Duration.ofMinutes(60L)
)

package org.mj.mansour.auth.jwt

import io.jsonwebtoken.Jwts
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date

@Component
class JwtTokenProvider(
    private val properties: JwtTokenProperties
) {

    private val privateKey: PrivateKey by lazy {
        val resource = ClassPathResource(properties.privateKeyPath.removePrefix("classpath:"))

        val privateKeyBase64 = resource.inputStream.readBytes().toString(Charsets.UTF_8).trim()

        val keyBytes = Base64.getDecoder().decode(privateKeyBase64)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        KeyFactory.getInstance("RSA").generatePrivate(keySpec)
    }

    fun generateToken(subject: String): String {
        val issuedAt = Date()
        val expiredMills = issuedAt.time + properties.expiration.toMillis()
        val expiration = Date(expiredMills)

        return Jwts.builder()
            .subject(subject)
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(privateKey)
            .compact()
    }

}

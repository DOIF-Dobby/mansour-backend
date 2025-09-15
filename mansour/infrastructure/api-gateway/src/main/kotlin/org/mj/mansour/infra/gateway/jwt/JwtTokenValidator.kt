package org.mj.mansour.infra.gateway.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64

@Component
class JwtTokenValidator(
    private val properties: JwtTokenProperties
) {

    private val publicKey: PublicKey by lazy {
        val resource = ClassPathResource(properties.publicKeyPath.removePrefix("classpath:"))

        val privateKeyBase64 = resource.inputStream.readBytes().toString(Charsets.UTF_8).trim()

        val keyBytes = Base64.getDecoder().decode(privateKeyBase64)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }

    /**
     * 토큰이 유효한지 검증하고 반환한다.
     * 이 과정에서 서명, 만료 시간 등이 올바르지 않으면 예외 발생
     */
    fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
    }

    fun getSubject(token: String): String {
        return getClaims(token).payload.subject
    }
}

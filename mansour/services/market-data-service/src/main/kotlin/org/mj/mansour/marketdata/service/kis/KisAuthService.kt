package org.mj.mansour.marketdata.service.kis

import org.mj.mansour.marketdata.config.kis.KisProperties
import org.mj.mansour.marketdata.dto.kis.KisTokenRequest
import org.mj.mansour.marketdata.dto.kis.KisTokenResponse
import org.mj.mansour.system.core.logging.log
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class KisAuthService(
    private val kisRestClient: RestClient,
    private val kisProperties: KisProperties,
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private val tokenKey = "marketdata:kis:token"

    /**
     * Redis 캐시에서 KIS 토큰을 가져오거나, 없으면 새로 발급받아 반환합니다.
     */
    fun getToken(): String {
        val token = getTokenFromCache()

        if (token != null) {
            log.info { "Retrieved token from Redis cache." }
            return token
        }

        val tokenResponse = issueToken()
        if (tokenResponse == null) {
            log.error { "Failed to issue KIS token." }
            throw RuntimeException("Failed to issue KIS token")
        }

        saveTokenToCache(tokenResponse)

        return tokenResponse.accessToken
    }

    /**
     * KIS API에 접근하기 위한 토큰을 발급합니다.
     */
    private fun issueToken(): KisTokenResponse? {
        log.info { "Issue kis token" }
        val request = KisTokenRequest(
            appKey = kisProperties.appKey,
            appSecret = kisProperties.appSecret
        )

        try {
            return kisRestClient.post()
                .uri("/oauth2/tokenP")
                .body(request)
                .retrieve()
                .body(KisTokenResponse::class.java)
        } catch (ex: Exception) {
            log.warn { "Failed to issue token. message: ${ex.message}" }
            return null
        }
    }

    /**
     * Redis 캐시에서 KIS 토큰을 가져옵니다.
     */
    private fun getTokenFromCache(): String? {
        return redisTemplate.opsForValue().get(tokenKey)
    }

    /**
     * KIS 토큰을 Redis 캐시에 저장합니다.
     */
    private fun saveTokenToCache(kisTokenResponse: KisTokenResponse) {
        val now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val duration = Duration.between(now, kisTokenResponse.accessTokenTokenExpired)

        val safeDuration = duration.minusMinutes(10) // 10분 여유를 두고 만료 시간을 설정
        val ttl = when {
            safeDuration.isNegative || safeDuration.isZero -> {
                if (duration.isNegative || duration.isZero) Duration.ofSeconds(1) else duration
            }

            else -> safeDuration
        }

        redisTemplate.opsForValue().set(
            tokenKey,
            kisTokenResponse.accessToken,
            ttl
        )
    }
}

package org.mj.mansour.marketdata.kis.service

import org.mj.mansour.marketdata.kis.config.KisProperties
import org.mj.mansour.marketdata.kis.constant.KisTokenType
import org.mj.mansour.marketdata.kis.dto.auth.KisApiTokenRequest
import org.mj.mansour.marketdata.kis.dto.auth.KisApiTokenResponse
import org.mj.mansour.marketdata.kis.dto.auth.KisRealtimeTokenRequest
import org.mj.mansour.marketdata.kis.dto.auth.KisRealtimeTokenResponse
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

    /**
     * KIS API 토큰을 가져옵니다.
     */
    fun getApiToken(): String {
        return getToken(KisTokenType.API)
    }

    /**
     * KIS 실시간 데이터 토큰을 가져옵니다.
     */
    fun getRealtimeToken(): String {
        return getToken(KisTokenType.REALTIME)
    }

    /**
     * Redis 캐시에서 KIS 토큰을 가져오거나, 없으면 새로 발급받아 반환합니다.
     */
    private fun getToken(tokenType: KisTokenType): String {
        val token = getTokenFromCache(tokenType)

        if (token != null) {
            log.info { "Retrieved token from Redis cache." }
            return token
        }

        when (tokenType) {
            KisTokenType.API -> {
                val tokenResponse = issueApiToken()
                if (tokenResponse == null) {
                    log.error { "Failed to issue KIS api token." }
                    throw RuntimeException("Failed to issue KIS api token")
                }

                saveApiTokenToCache(tokenResponse)

                return tokenResponse.accessToken
            }

            KisTokenType.REALTIME -> {
                val tokenResponse = issueRealtimeToken()

                if (tokenResponse == null) {
                    log.error { "Failed to issue KIS realtime token." }
                    throw RuntimeException("Failed to issue KIS realtime token")
                }

                saveRealtimeTokenToCache(tokenResponse)

                return tokenResponse.approvalKey
            }
        }
    }

    /**
     * KIS API에 접근하기 위한 토큰을 발급합니다.
     */
    private fun issueApiToken(): KisApiTokenResponse? {
        log.info { "Issue kis token" }
        val request = KisApiTokenRequest(
            appKey = kisProperties.appKey,
            appSecret = kisProperties.appSecret
        )

        try {
            return kisRestClient.post()
                .uri("/oauth2/tokenP")
                .body(request)
                .retrieve()
                .body(KisApiTokenResponse::class.java)
        } catch (ex: Exception) {
            log.warn { "Failed to issue api token. message: ${ex.message}" }
            return null
        }
    }

    /**
     * KIS 실시간 데이터에 접근하기 위한 토큰을 발급합니다.
     */
    private fun issueRealtimeToken(): KisRealtimeTokenResponse? {
        log.info { "Issue kis realtime token" }
        val request = KisRealtimeTokenRequest(
            appKey = kisProperties.appKey,
            secretKey = kisProperties.appSecret
        )

        try {
            return kisRestClient.post()
                .uri("/oauth2/Approval")
                .body(request)
                .retrieve()
                .body(KisRealtimeTokenResponse::class.java)
        } catch (ex: Exception) {
            log.warn { "Failed to issue realtime token. message: ${ex.message}" }
            return null
        }
    }

    /**
     * Redis 캐시에서 KIS 토큰을 가져옵니다.
     */
    private fun getTokenFromCache(tokenType: KisTokenType): String? {
        return redisTemplate.opsForValue().get(tokenType.tokenKey)
    }

    /**
     * KIS API 토큰을 Redis 캐시에 저장합니다.
     */
    private fun saveApiTokenToCache(tokenResponse: KisApiTokenResponse) {
        val now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val duration = Duration.between(now, tokenResponse.accessTokenTokenExpired)

        val safeDuration = duration.minusMinutes(10) // 10분 여유를 두고 만료 시간을 설정
        val ttl = when {
            safeDuration.isNegative || safeDuration.isZero -> {
                if (duration.isNegative || duration.isZero) Duration.ofSeconds(1) else duration
            }

            else -> safeDuration
        }

        redisTemplate.opsForValue().set(
            KisTokenType.API.tokenKey,
            tokenResponse.accessToken,
            ttl
        )
    }

    /**
     * KIS 실시간 토큰을 Redis 캐시에 저장합니다.
     */
    private fun saveRealtimeTokenToCache(tokenResponse: KisRealtimeTokenResponse) {
        val now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val duration = Duration.between(now, now.plusHours(23)) // 명세는 24시간이지만, 1시간 여유를 두고 23시간으로 설정

        redisTemplate.opsForValue().set(
            KisTokenType.REALTIME.tokenKey,
            tokenResponse.approvalKey,
            duration
        )
    }
}

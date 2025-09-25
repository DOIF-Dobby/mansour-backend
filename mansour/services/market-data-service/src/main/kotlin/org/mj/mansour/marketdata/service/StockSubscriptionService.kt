package org.mj.mansour.marketdata.service

import org.mansour.shared.domain.enums.MarketType
import org.mj.mansour.marketdata.redis.MarketDataRedisKeys
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class StockSubscriptionService(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    /**
     * 사용자가 특정 주식에 구독을 시작합니다.
     */
    fun subscribe(userId: Long, symbol: String, market: MarketType) {
        val subscribersKey = MarketDataRedisKeys.getSubscribersKey(symbol, market)

        // 주식별 구독자 Set에 사용자 추가
        val isNewSubscriber = redisTemplate.opsForSet().add(subscribersKey, userId.toString()) == 1L

        // 새로운 구독자인 경우
        if (isNewSubscriber) {
            // 현재 구독자 수 확인
            val subscriberCount = redisTemplate.opsForSet().size(subscribersKey)

            // 이 사용자가 첫 구독자라면, 활성 주식 목록에 추가
            if (subscriberCount == 1L) {
                val activeStocksKey = MarketDataRedisKeys.getActiveStocksKey(market)
                redisTemplate.opsForSet().add(activeStocksKey, symbol)
            }
        }
    }

    /**
     * 사용자가 특정 주식에 대한 구독을 취소합니다.
     */
    fun unsubscribe(symbol: String, userId: Long, market: MarketType) {
        val subscribersKey = MarketDataRedisKeys.getSubscribersKey(symbol, market)

        // 1. 주식별 구독자 Set에서 사용자 제거
        val wasSubscriber = redisTemplate.opsForSet().remove(subscribersKey, userId.toString()) == 1L

        // 2. 실제로 구독자였던 경우에만 로직 실행
        if (wasSubscriber) {
            // 3. 현재 구독자 수 확인
            val subscriberCount = redisTemplate.opsForSet().size(subscribersKey)

            // 4. 마지막 구독자였다면, 활성 주식 목록에서 제거
            if (subscriberCount == 0L) {
                val activeStocksKey = MarketDataRedisKeys.getActiveStocksKey(market)
                redisTemplate.opsForSet().remove(activeStocksKey, symbol)
            }
        }
    }

}

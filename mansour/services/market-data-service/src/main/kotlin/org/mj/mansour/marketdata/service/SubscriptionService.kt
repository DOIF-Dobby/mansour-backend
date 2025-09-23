package org.mj.mansour.marketdata.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SubscriptionService(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private val activeStocksKey = "marketdata:active-stocks"
    private fun getSubscribersKey(symbol: String) = "marketdata:stock:$symbol:subscribers"

    fun subscribe(symbol: String, userId: Long) {
        val subscribersKey = getSubscribersKey(symbol)

        // 주식별 구독자 Set에 사용자 추가
        val isNewSubscriber = redisTemplate.opsForSet().add(subscribersKey, userId.toString()) == 1L

        // 새로운 구독자인 경우
        if (isNewSubscriber) {
            // 현재 구독자 수 확인
            val subscriberCount = redisTemplate.opsForSet().size(subscribersKey)

            // 이 사용자가 첫 구독자라면, 활성 주식 목록에 추가
            if (subscriberCount == 1L) {
                redisTemplate.opsForSet().add(activeStocksKey, symbol)
            }
        }
    }

}

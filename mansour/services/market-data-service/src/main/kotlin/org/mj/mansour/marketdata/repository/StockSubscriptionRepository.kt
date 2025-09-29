package org.mj.mansour.marketdata.repository

import org.mansour.shared.domain.enums.MarketType
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class StockSubscriptionRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private fun getActiveStocksKey(market: MarketType) = "marketdata:active-stocks:${market.location}"
    private fun getSubscribersKey(symbol: String, market: MarketType) = "marketdata:stock:${market.location}:$symbol:subscribers"
    private fun getActiveDomesticStocksKey() = "marketdata:active-stocks:domestic"

    /**
     * 특정 종목의 구독자 Set에 사용자를 추가합니다.
     * @return 새로운 구독자이면 true, 이미 구독 중이었으면 false
     */
    fun addSubscriber(symbol: String, market: MarketType, userId: Long): Boolean {
        val subscribersKey = getSubscribersKey(symbol, market)
        return redisTemplate.opsForSet().add(subscribersKey, userId.toString()) == 1L
    }

    /**
     * 특정 종목의 현재 구독자 수를 반환합니다.
     */
    fun getSubscriberCount(symbol: String, market: MarketType): Long {
        val subscribersKey = getSubscribersKey(symbol, market)
        return redisTemplate.opsForSet().size(subscribersKey) ?: 0L
    }

    /**
     * 활성 주식 목록 Set에 종목을 추가합니다.
     */
    fun addActiveStock(symbol: String, market: MarketType) {
        val activeStocksKey = getActiveStocksKey(market)
        redisTemplate.opsForSet().add(activeStocksKey, symbol)
    }

    /**
     * 특정 종목의 구독자 Set에서 사용자를 제거합니다.
     * @return 성공적으로 제거되었으면 true, 원래 구독자가 아니었으면 false
     */
    fun removeSubscriber(symbol: String, market: MarketType, userId: Long): Boolean {
        val subscribersKey = getSubscribersKey(symbol, market)
        return redisTemplate.opsForSet().remove(subscribersKey, userId.toString()) == 1L
    }

    /**
     * 활성 주식 목록 Set에서 종목을 제거합니다.
     */
    fun removeActiveStock(symbol: String, market: MarketType) {
        val activeStocksKey = getActiveStocksKey(market)
        redisTemplate.opsForSet().remove(activeStocksKey, symbol)
    }

    /**
     * 현재 활성화된 국내 주식 목록을 반환합니다.
     */
    fun getActiveDomesticStocks(): Set<String> {
        return redisTemplate.opsForSet().members(getActiveDomesticStocksKey()) ?: emptySet()
    }
}

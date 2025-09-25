package org.mj.mansour.marketdata.redis

import org.mansour.shared.domain.enums.MarketType

object MarketDataRedisKeys {

    fun getActiveStocksKey(market: MarketType) = "marketdata:active-stocks:${market.location}"
    fun getSubscribersKey(symbol: String, market: MarketType) = "marketdata:stock:${market.location}:$symbol:subscribers"

    fun getActiveDomesticStocksKey() = "marketdata:active-stocks:domestic"
}

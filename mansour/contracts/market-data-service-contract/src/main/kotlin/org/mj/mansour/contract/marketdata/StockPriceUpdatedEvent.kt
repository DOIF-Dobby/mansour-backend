package org.mj.mansour.contract.marketdata

import java.math.BigDecimal
import java.time.Instant

object StockPriceUpdatedEvent {
    const val TOPIC = "stock.price.updated"

    data class Payload(
        val timestamp: Instant,
        val symbol: String,
        val open: BigDecimal,
        val high: BigDecimal,
        val low: BigDecimal,
        val close: BigDecimal,
        val volume: BigDecimal, // 누적 거래량
        val tradeVolume: Long, // 체결 거래량
    )
}

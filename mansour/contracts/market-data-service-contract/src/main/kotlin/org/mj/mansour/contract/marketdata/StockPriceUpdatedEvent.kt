package org.mj.mansour.contract.marketdata

import java.math.BigDecimal
import java.time.Instant

object StockPriceUpdatedEvent {
    const val AGGREGATE_TYPE = "StockPrice"
    const val EVENT_TYPE = "StockPriceUpdatedEvent"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val timestamp: Instant,
        val symbol: String,
        val open: BigDecimal,
        val high: BigDecimal,
        val low: BigDecimal,
        val close: BigDecimal,
        val volume: BigDecimal
    )
}

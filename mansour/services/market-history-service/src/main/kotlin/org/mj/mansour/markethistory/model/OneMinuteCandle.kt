package org.mj.mansour.markethistory.model

import java.math.BigDecimal
import java.time.Instant

data class OneMinuteCandle(
    val symbol: String,
    val open: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val close: BigDecimal,
    val volume: BigDecimal,
    val windowStartTime: Instant,
    val windowEndTime: Instant,
)

package org.mj.mansour.markethistory.dto

import java.math.BigDecimal

data class CandleResponse(
    val time: Long,
    val symbol: String,
    val open: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val close: BigDecimal,
    val volume: Long,
)

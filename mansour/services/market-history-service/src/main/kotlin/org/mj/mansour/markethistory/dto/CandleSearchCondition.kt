package org.mj.mansour.markethistory.dto

import org.mj.mansour.markethistory.enums.Resolution
import java.time.LocalDateTime

data class CandleSearchCondition(
    val symbol: String,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val resolution: Resolution,
)

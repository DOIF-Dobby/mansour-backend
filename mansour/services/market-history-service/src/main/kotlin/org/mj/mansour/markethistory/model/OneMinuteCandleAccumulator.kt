package org.mj.mansour.markethistory.model

import java.math.BigDecimal

data class OneMinuteCandleAccumulator(
    var symbol: String = "",
    var open: BigDecimal = BigDecimal.ZERO,
    var high: BigDecimal = BigDecimal.ZERO,
    var low: BigDecimal = BigDecimal.ZERO,
    var close: BigDecimal = BigDecimal.ZERO,
    var volume: BigDecimal = BigDecimal.ZERO
)

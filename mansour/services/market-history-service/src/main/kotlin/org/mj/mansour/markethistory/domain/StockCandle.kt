package org.mj.mansour.markethistory.domain

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.math.BigDecimal
import java.time.Instant

@Measurement(name = "stock_candle")
class StockCandle(
    @Column(timestamp = true)
    val windowStartTime: Instant,

    @Column(tag = true)
    val symbol: String,

    @Column
    val open: BigDecimal,

    @Column
    val high: BigDecimal,

    @Column
    val low: BigDecimal,

    @Column
    val close: BigDecimal,

    @Column
    val volume: BigDecimal,

    @Column
    val windowEndTime: Instant,
) {
}

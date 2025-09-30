package org.mj.mansour.markethistory.domain

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.math.BigDecimal
import java.time.Instant

@Measurement(name = "stock_candle")
data class StockCandle(
    @Column(timestamp = true)
    val windowStartTime: Instant,

    @Column(tag = true, name = "symbol")
    val symbol: String,

    @Column(name = "open")
    val open: BigDecimal,

    @Column(name = "high")
    val high: BigDecimal,

    @Column(name = "low")
    val low: BigDecimal,

    @Column(name = "close")
    val close: BigDecimal,

    @Column(name = "volume")
    val volume: Long,

    @Column(name = "windowEndTime")
    val windowEndTime: Instant,
) {
}

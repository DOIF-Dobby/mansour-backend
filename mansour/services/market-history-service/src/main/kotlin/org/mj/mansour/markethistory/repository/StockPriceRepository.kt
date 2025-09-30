package org.mj.mansour.markethistory.repository

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import org.mj.mansour.markethistory.domain.StockCandle
import org.mj.mansour.markethistory.influx.InfluxQueryBuilder
import org.mj.mansour.system.core.logging.log
import org.springframework.stereotype.Repository

@Repository
class StockPriceRepository(
    private val influxDBClient: InfluxDBClient,
    private val stockCandleMapper: StockCandleMapper
) {

    fun save(candle: StockCandle) {
        val writeApi = influxDBClient.writeApiBlocking
        writeApi.writeMeasurement(WritePrecision.S, candle)
        log.info { "Saved candle $candle" }
    }


    fun findRecentCandles(symbol: String, limit: Int = 100): List<StockCandle> {
        return InfluxQueryBuilder("market_history_data", stockCandleMapper)
            .measurement("stock_candle")
            .tag("symbol", symbol)
            .range("-7d")
            .limit(limit)
            .execute(influxDBClient.queryApi)
    }
}

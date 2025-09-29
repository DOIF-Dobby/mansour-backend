package org.mj.mansour.markethistory.repository

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import com.influxdb.query.FluxTable
import org.mj.mansour.markethistory.domain.StockCandle
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.Instant

@Repository
class StockPriceRepository(
    private val influxDBClient: InfluxDBClient,
) {
    fun save(candle: StockCandle) {
        val writeApi = influxDBClient.makeWriteApi()
        writeApi.writeMeasurement(WritePrecision.S, candle)
    }

    /**
     * 특정 종목의 최근 100개 1분봉 데이터를 시간순으로 조회합니다.
     */
    fun findRecentCandles(symbol: String, limit: Int = 100): List<StockCandle> {
        // 1. 쿼리를 실행할 QueryApi 객체를 가져옵니다.
        val queryApi = influxDBClient.queryApi

        // 2. Flux 쿼리를 작성합니다.
        val fluxQuery = """
            from(bucket: "market_data")
              |> range(start: -7d)
              |> filter(fn: (r) => r._measurement == "stock_candle" and r.symbol == "$symbol")
              |> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
              |> rename(columns: {_time: "windowStartTime"}) // 1. _time 컬럼을 DTO의 필드명과 일치시킵니다.
              |> keep(columns: [
                    "windowStartTime", "symbol", "open", "high", "low",
                    "close", "volume", "windowEndTime"
                 ]) // 2. DTO에 정의된 필드만 정확히 선택합니다.
              |> sort(columns: ["windowStartTime"], desc: true) // 3. 정렬 기준을 명확히 합니다.
              |> limit(n: $limit)
        """.trimIndent()


        val tables: List<FluxTable> = queryApi.query(fluxQuery)
        val candles = mutableListOf<StockCandle>()

        for (table in tables) {
            for (record in table.records) {
                candles.add(
                    StockCandle(
                        // record.time은 Instant? 타입입니다.
                        windowStartTime = record.time ?: Instant.EPOCH,
                        // record.getValueByKey는 Any? 타입이므로, 안전하게 캐스팅합니다.
                        symbol = record.getValueByKey("symbol") as? String ?: "",
                        open = record.getValueByKey("open") as? BigDecimal ?: BigDecimal.ZERO,
                        high = record.getValueByKey("high") as? BigDecimal ?: BigDecimal.ZERO,
                        low = record.getValueByKey("low") as? BigDecimal ?: BigDecimal.ZERO,
                        close = record.getValueByKey("close") as? BigDecimal ?: BigDecimal.ZERO,
                        volume = record.getValueByKey("volume") as? BigDecimal ?: BigDecimal.ZERO,
                        // 3. windowEndTime을 String으로 받아 Instant로 직접 파싱합니다.
                        windowEndTime = (record.getValueByKey("windowEndTime") as? String)
                            ?.let { Instant.parse(it) } ?: Instant.EPOCH
                    )
                )
            }
        }
        return candles

    }
}

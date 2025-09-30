package org.mj.mansour.markethistory.repository

import com.influxdb.client.InfluxDBClient
import org.mj.mansour.markethistory.domain.StockCandle
import org.mj.mansour.markethistory.enums.Resolution
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId

@Repository
class StockChartRepository(
    private val influxDBClient: InfluxDBClient,
    private val stockCandleMapper: StockCandleMapper
) {

    fun findCandles(symbol: String, resolution: Resolution, from: LocalDateTime, to: LocalDateTime): List<StockCandle> {
        val queryApi = influxDBClient.queryApi

        val interval = resolution.value

        val start = from.atZone(ZoneId.systemDefault()).toInstant().epochSecond
        val stop = to.atZone(ZoneId.systemDefault()).toInstant().epochSecond

        val fluxQuery = """
        data = from(bucket: "market_history_data")
          |> range(start: ${start}, stop: ${stop})
          |> filter(fn: (r) => r._measurement == "stock_candle" and r.symbol == "$symbol")

        // 각 필드별로 집계
        openData = data
          |> filter(fn: (r) => r._field == "open")
          |> aggregateWindow(every: $interval, fn: first, createEmpty: false)

        highData = data
          |> filter(fn: (r) => r._field == "high")
          |> aggregateWindow(every: $interval, fn: max, createEmpty: false)

        lowData = data
          |> filter(fn: (r) => r._field == "low")
          |> aggregateWindow(every: $interval, fn: min, createEmpty: false)

        closeData = data
          |> filter(fn: (r) => r._field == "close")
          |> aggregateWindow(every: $interval, fn: last, createEmpty: false)

        volumeData = data
          |> filter(fn: (r) => r._field == "volume")
          |> aggregateWindow(every: $interval, fn: sum, createEmpty: false)

        // 합치기
        union(tables: [openData, highData, lowData, closeData, volumeData])
          |> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
          |> sort(columns: ["_time"], desc: true)
    """.trimIndent()

        return queryApi.query(fluxQuery)
            .flatMap { table -> table.records.map { stockCandleMapper.map(it) } }
    }
}

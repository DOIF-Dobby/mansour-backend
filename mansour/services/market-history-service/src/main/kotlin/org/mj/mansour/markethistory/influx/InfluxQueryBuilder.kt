package org.mj.mansour.markethistory.influx

import com.influxdb.client.QueryApi

class InfluxQueryBuilder<T : Any>(
    private val bucket: String,
    private val mapper: FluxRecordMapper<T>
) {
    private val filters = mutableListOf<String>()
    private var rangeStart = "-7d"
    private var limitCount = 100

    fun measurement(name: String) = apply {
        filters.add("""r._measurement == "$name"""")
    }

    fun tag(key: String, value: String) = apply {
        filters.add("""r.$key == "$value"""")
    }

    fun range(start: String) = apply { rangeStart = start }
    fun limit(n: Int) = apply { limitCount = n }

    fun execute(queryApi: QueryApi): List<T> {
        val filterExpr = filters.joinToString(" and ")
        val query = """
            from(bucket: "$bucket")
              |> range(start: $rangeStart)
              |> filter(fn: (r) => $filterExpr)
              |> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
              |> sort(columns: ["_time"], desc: true)
              |> limit(n: $limitCount)
        """.trimIndent()

        return queryApi.query(query)
            .flatMap { table -> table.records.map { mapper.map(it) } }
    }
}

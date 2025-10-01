package org.mj.mansour.markethistory.influx

import com.influxdb.query.FluxRecord
import java.time.Instant

interface FluxRecordMapper<T> {
    fun map(record: FluxRecord): T
}

fun FluxRecord.getInstant(key: String): Instant =
    getValueByKey(key) as? Instant ?: Instant.EPOCH

fun FluxRecord.getString(key: String): String =
    getValueByKey(key) as? String ?: ""

fun FluxRecord.getDouble(key: String): Double =
    getValueByKey(key) as? Double ?: 0.0

fun FluxRecord.getLong(key: String): Long =
    getValueByKey(key) as? Long ?: 0L

fun FluxRecord.getInstantFromString(key: String): Instant =
    (getValueByKey(key) as? String)?.let { Instant.parse(it) } ?: Instant.EPOCH

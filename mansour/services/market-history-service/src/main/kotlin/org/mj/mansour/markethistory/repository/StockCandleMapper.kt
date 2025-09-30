package org.mj.mansour.markethistory.repository

import com.influxdb.query.FluxRecord
import org.mj.mansour.markethistory.domain.StockCandle
import org.mj.mansour.markethistory.influx.FluxRecordMapper
import org.mj.mansour.markethistory.influx.getDouble
import org.mj.mansour.markethistory.influx.getInstantFromString
import org.mj.mansour.markethistory.influx.getLong
import org.mj.mansour.markethistory.influx.getString
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class StockCandleMapper : FluxRecordMapper<StockCandle> {
    override fun map(record: FluxRecord): StockCandle {
        return StockCandle(
            windowStartTime = record.time ?: Instant.EPOCH,
            symbol = record.getString("symbol"),
            open = record.getDouble("open").toBigDecimal(),
            high = record.getDouble("high").toBigDecimal(),
            low = record.getDouble("low").toBigDecimal(),
            close = record.getDouble("close").toBigDecimal(),
            volume = record.getLong("volume"),
            windowEndTime = record.getInstantFromString("windowEndTime")
        )
    }
}

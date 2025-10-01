package org.mj.mansour.markethistory.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.markethistory.config.MarketDataStreamConfig.Companion.CANDLE_TOPIC
import org.mj.mansour.markethistory.domain.StockCandle
import org.mj.mansour.markethistory.model.OneMinuteCandle
import org.mj.mansour.markethistory.repository.StockPriceRepository
import org.mj.mansour.system.core.logging.log
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CandleDbWriter(
    private val objectMapper: ObjectMapper,
    private val stockPriceRepository: StockPriceRepository,
) {

    @KafkaListener(topics = [CANDLE_TOPIC])
    fun handleCandleEvent(message: String) {
        val candle = objectMapper.readValue(message, OneMinuteCandle::class.java)
        log.info { "Parsed candle: $candle" }

        stockPriceRepository.save(
            StockCandle(
                symbol = candle.symbol,
                open = candle.open,
                high = candle.high,
                low = candle.low,
                close = candle.close,
                volume = candle.volume,
                windowStartTime = candle.windowStartTime,
                windowEndTime = candle.windowEndTime
            )
        )
    }
}

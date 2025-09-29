package org.mj.mansour.markethistory.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.markethistory.config.MarketDataStreamConfig.Companion.CANDLE_TOPIC
import org.mj.mansour.markethistory.model.OneMinuteCandle
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CandleDbWriter(
    private val objectMapper: ObjectMapper,
) {

    @KafkaListener(topics = [CANDLE_TOPIC])
    fun handleCandleEvent(message: String) {
        println("Received candle event: $message")

        val candle = objectMapper.readValue(message, OneMinuteCandle::class.java)
        println("Parsed candle: $candle")
    }
}

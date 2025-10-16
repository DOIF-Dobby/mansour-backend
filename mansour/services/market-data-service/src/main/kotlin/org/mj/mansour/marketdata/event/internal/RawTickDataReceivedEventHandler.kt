package org.mj.mansour.marketdata.event.internal

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.contract.marketdata.StockPriceUpdatedEvent
import org.mj.mansour.system.core.logging.log
import org.springframework.context.event.EventListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class RawTickDataReceivedEventHandler(
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HHmmss")

    @EventListener(RawTickDataReceivedEvent::class)
    fun handle(event: RawTickDataReceivedEvent) {
        val rawData = event.priceData

        val businessDate = LocalDate.parse(rawData.businessDate, dateFormatter)
        val executionTime = LocalTime.parse(rawData.executionTime, timeFormatter)

        val localDateTime = LocalDateTime.of(businessDate, executionTime)
        val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
        val timestamp = zonedDateTime.toInstant()

        val eventPayload = StockPriceUpdatedEvent.Payload(
            timestamp = timestamp,
            symbol = rawData.symbol,
            open = rawData.openingPrice,
            high = rawData.highestPrice,
            low = rawData.lowestPrice,
            close = rawData.currentPrice,
            volume = rawData.accumulatedVolume,
            tradeVolume = rawData.tradeVolume,
        )

        try {
            kafkaTemplate.send(
                StockPriceUpdatedEvent.TOPIC,
                objectMapper.writeValueAsString(eventPayload)
            )
        } catch (_: Exception) {
            log.error { "Failed to send message to Kafka topic" }
        }


    }
}

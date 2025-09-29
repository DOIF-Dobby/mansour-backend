package org.mj.mansour.marketdata.event

import org.mj.mansour.contract.marketdata.StockPriceUpdatedEvent
import org.mj.mansour.marketdata.service.OutboxService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class RawTickDataReceivedEventHandler(
    private val outboxService: OutboxService,
) {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HHmmss")

    @EventListener(RawTickDataReceivedEvent::class)
    fun handle(event: RawTickDataReceivedEvent) {
        val rawData = event.priceData

        val seoulZoneId = ZoneId.of("Asia/Seoul")

        val businessDate = LocalDate.parse(rawData.businessDate, dateFormatter)
        val executionTime = LocalTime.parse(rawData.executionTime, timeFormatter)

        val localDateTime = LocalDateTime.of(businessDate, executionTime)
        val zonedDateTime = localDateTime.atZone(seoulZoneId)
        val timestamp = zonedDateTime.toInstant()

        val eventPayload = StockPriceUpdatedEvent.Payload(
            timestamp = timestamp,
            symbol = rawData.symbol,
            open = rawData.openingPrice,
            high = rawData.highestPrice,
            low = rawData.lowestPrice,
            close = rawData.currentPrice,
            volume = rawData.accumulatedVolume
        )

        outboxService.saveOutboxRecord(
            aggregateId = eventPayload.symbol,
            aggregateType = StockPriceUpdatedEvent.AGGREGATE_TYPE,
            eventType = StockPriceUpdatedEvent.EVENT_TYPE,
            payload = eventPayload
        )
    }
}

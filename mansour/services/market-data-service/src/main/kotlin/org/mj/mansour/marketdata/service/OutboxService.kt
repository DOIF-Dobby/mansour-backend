package org.mj.mansour.marketdata.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.marketdata.domain.outbox.Outbox
import org.mj.mansour.marketdata.domain.outbox.OutboxRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OutboxService(
    private val outboxRepository: OutboxRepository,
) {

    private val objectMapper = ObjectMapper()

    @Transactional
    fun saveOutboxRecord(aggregateType: String, aggregateId: String, eventType: String, payload: Any) {
        val payloadString = objectMapper.writeValueAsString(payload)
        val outbox = Outbox(
            aggregateType = aggregateType,
            aggregateId = aggregateId,
            eventType = eventType,
            payload = payloadString
        )
        outboxRepository.save(outbox)
    }
}

package org.mj.mansour.activity.event

import org.mj.mansour.activity.domain.outbox.Outbox
import org.mj.mansour.activity.domain.outbox.OutboxRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OutboxRecordListener(
    private val outboxRepository: OutboxRepository,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun recordInterestAssetAddedEvent(event: InterestAssetAddedEvent) {

        val outbox = Outbox(
            aggregateType = "InterestAsset",
            aggregateId = event.id.toString(),
            eventType = InterestAssetAddedEvent::class.simpleName ?: "UnknownEvent",
            payload = event.payload
        )

        outboxRepository.save(outbox)
    }
}

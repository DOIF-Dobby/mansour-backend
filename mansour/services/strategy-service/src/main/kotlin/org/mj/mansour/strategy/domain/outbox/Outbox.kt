package org.mj.mansour.strategy.domain.outbox

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.mj.mansour.system.data.jpa.BaseOutboxEntity
import java.util.UUID

@Entity
class Outbox(
    aggregateType: String,
    aggregateId: String,
    eventType: String,
    payload: String
) : BaseOutboxEntity(
    aggregateType = aggregateType,
    aggregateId = aggregateId,
    eventType = eventType,
    payload = payload
) {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val uuid: UUID = UUID.randomUUID()

    override fun getId(): String = uuid.toString()
}

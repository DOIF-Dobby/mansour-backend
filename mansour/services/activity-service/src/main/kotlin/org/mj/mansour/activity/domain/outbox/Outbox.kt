package org.mj.mansour.activity.domain.outbox

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
    val id: UUID = UUID.randomUUID()
}

package org.mj.mansour.marketdata.domain.outbox

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OutboxRepository : JpaRepository<Outbox, UUID> {
}

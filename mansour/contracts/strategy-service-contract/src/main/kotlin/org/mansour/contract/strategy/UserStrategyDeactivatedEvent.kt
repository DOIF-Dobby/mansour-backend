package org.mansour.contract.strategy

object UserStrategyDeactivatedEvent {
    const val AGGREGATE_TYPE = "UserStrategy"
    const val EVENT_TYPE = "UserStrategyDeactivatedEvent"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val userStrategyId: Long,
        val userId: Long,
        val strategyId: String,
        val symbol: String,
    )
}

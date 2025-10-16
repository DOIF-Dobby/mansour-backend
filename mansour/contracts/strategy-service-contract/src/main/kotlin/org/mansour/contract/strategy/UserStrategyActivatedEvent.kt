package org.mansour.contract.strategy

object UserStrategyActivatedEvent {
    const val AGGREGATE_TYPE = "UserStrategy"
    const val EVENT_TYPE = "UserStrategyActivatedEvent"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val userStrategyId: Long,
        val userId: Long,
        val strategyId: String,
        val symbol: String,
        val parameters: Map<String, String>,
    )
}

package org.mj.mansour.contract.activity

object InterestAssetRemovedEvent {
    const val AGGREGATE_TYPE = "InterestAsset"
    const val EVENT_TYPE = "InterestAssetRemovedEvent"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val groupId: Long,
        val assetId: Long,
        val userId: Long,
    )
}

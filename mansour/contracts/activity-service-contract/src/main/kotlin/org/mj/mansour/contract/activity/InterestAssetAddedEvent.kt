package org.mj.mansour.contract.activity

object InterestAssetAddedEvent {
    const val AGGREGATE_TYPE = "InterestAsset"
    const val EVENT_TYPE = "InterestAssetAddedEvent"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val interestAssetId: Long,
        val assetId: Long,
        val assetSymbol: String,
        val userId: Long
    )
}

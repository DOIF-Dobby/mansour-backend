package org.mj.mansour.contract.marketdata

object UndoAddInterestAssetCommand {
    const val AGGREGATE_TYPE = "InterestAsset"
    const val EVENT_TYPE = "UndoAddInterestAssetCommand"
    const val TOPIC = "outbox.event.$AGGREGATE_TYPE.$EVENT_TYPE"

    data class Payload(
        val groupId: Long,
        val assetId: Long,
        val userId: Long,
        val interestAssetId: Long,
    )
}

package org.mj.mansour.marketdata.event.consumer

import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.mj.mansour.contract.asset.StockResponse
import org.mj.mansour.contract.marketdata.UndoAddInterestAssetCommand
import org.mj.mansour.marketdata.client.AssetServiceClient
import org.mj.mansour.marketdata.enums.SubscriptionSourceType
import org.mj.mansour.marketdata.exception.InvalidRequestAssetDataException
import org.mj.mansour.marketdata.service.OutboxService
import org.mj.mansour.marketdata.service.StockSubscriptionService
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.feign.FeignClientExecutor
import org.mj.mansour.system.json.DebeziumMessageParser
import org.mj.mansour.system.json.parsePayload
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class InterestAssetAddedEventHandler(
    private val debeziumMessageParser: DebeziumMessageParser,
    private val stockSubscriptionService: StockSubscriptionService,
    private val assetServiceClient: AssetServiceClient,
    private val feignClientExecutor: FeignClientExecutor,
    private val outboxService: OutboxService,
) {

    /**
     * 관심 자산 추가 이벤트를 처리합니다.
     */
    @KafkaListener(topics = [InterestAssetAddedEvent.TOPIC])
    fun handle(@Payload message: String) {
        val payload = debeziumMessageParser.parsePayload<InterestAssetAddedEvent.Payload>(rawMessage = message)
        log.info { "handle InterestAssetAddedEvent. Received payload: $payload" }

        try {
            val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetById(payload.assetId) }.data
                ?: throw InvalidRequestAssetDataException()

            when (assetResponse) {
                is StockResponse -> {
                    stockSubscriptionService.subscribe(
                        userId = payload.userId,
                        symbol = assetResponse.symbol,
                        market = assetResponse.market,
                        sourceType = SubscriptionSourceType.INTEREST_ASSET,
                        sourceId = payload.interestAssetId
                    )
                }
            }
        } catch (e: Exception) {
            log.error(e) { "Failed to handle InterestAssetAddedEvent. Message: $message" }

            outboxService.saveOutboxRecord(
                aggregateId = payload.interestAssetId.toString(),
                aggregateType = UndoAddInterestAssetCommand.AGGREGATE_TYPE,
                eventType = UndoAddInterestAssetCommand.EVENT_TYPE,
                payload = UndoAddInterestAssetCommand.Payload(
                    groupId = payload.groupId,
                    assetId = payload.assetId,
                    userId = payload.userId,
                    interestAssetId = payload.interestAssetId
                )
            )
        }

    }
}

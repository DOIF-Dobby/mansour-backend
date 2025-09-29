package org.mj.mansour.marketdata.event.consumer

import org.mj.mansour.contract.activity.InterestAssetRemovedEvent
import org.mj.mansour.contract.asset.StockResponse
import org.mj.mansour.marketdata.client.AssetServiceClient
import org.mj.mansour.marketdata.exception.InvalidRequestAssetDataException
import org.mj.mansour.marketdata.service.StockSubscriptionService
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.feign.FeignClientExecutor
import org.mj.mansour.system.json.DebeziumMessageParser
import org.mj.mansour.system.json.parsePayload
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class InterestAssetRemovedEventHandler(
    private val debeziumMessageParser: DebeziumMessageParser,
    private val stockSubscriptionService: StockSubscriptionService,
    private val assetServiceClient: AssetServiceClient,
    private val feignClientExecutor: FeignClientExecutor,
) {

    @RetryableTopic(
        attempts = "4",
        backoff = Backoff(delay = 1000, multiplier = 2.0),
        dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = [InterestAssetRemovedEvent.TOPIC])
    fun handle(@Payload message: String) {
        val payload = debeziumMessageParser.parsePayload<InterestAssetRemovedEvent.Payload>(rawMessage = message)
        log.info { "handle InterestAssetRemovedEvent. Received payload: $payload" }

        val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetById(payload.assetId) }.data
            ?: throw InvalidRequestAssetDataException()

        when (assetResponse) {
            is StockResponse -> {
                stockSubscriptionService.unsubscribe(
                    userId = payload.userId,
                    symbol = assetResponse.symbol,
                    market = assetResponse.market,
                )
            }
        }

    }
}

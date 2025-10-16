package org.mj.mansour.marketdata.event.consumer

import org.mansour.contract.strategy.UserStrategyDeactivatedEvent
import org.mj.mansour.contract.asset.StockResponse
import org.mj.mansour.marketdata.client.AssetServiceClient
import org.mj.mansour.marketdata.enums.SubscriptionSourceType
import org.mj.mansour.marketdata.exception.InvalidRequestAssetDataException
import org.mj.mansour.marketdata.service.StockSubscriptionService
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.feign.FeignClientExecutor
import org.mj.mansour.system.json.DebeziumMessageParser
import org.mj.mansour.system.json.parsePayload
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserStrategyDeactivatedEventHandler(
    private val debeziumMessageParser: DebeziumMessageParser,
    private val stockSubscriptionService: StockSubscriptionService,
    private val assetServiceClient: AssetServiceClient,
    private val feignClientExecutor: FeignClientExecutor,
) {

    @KafkaListener(topics = [UserStrategyDeactivatedEvent.TOPIC])
    fun handle(message: String) {
        val payload = debeziumMessageParser.parsePayload<UserStrategyDeactivatedEvent.Payload>(rawMessage = message)
        log.info { "handle UserStrategyDeactivatedEvent. Received payload: $payload" }

        try {
            val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetBySymbol(payload.symbol) }.data
                ?: throw InvalidRequestAssetDataException()

            when (assetResponse) {
                is StockResponse -> {
                    stockSubscriptionService.unsubscribe(
                        userId = payload.userId,
                        symbol = assetResponse.symbol,
                        market = assetResponse.market,
                        sourceType = SubscriptionSourceType.USER_STRATEGY,
                        sourceId = payload.userStrategyId,
                    )
                }
            }
        } catch (e: Exception) {
            // TODO: 보상 트랜잭션 처리 로직 추가
        }
    }
}

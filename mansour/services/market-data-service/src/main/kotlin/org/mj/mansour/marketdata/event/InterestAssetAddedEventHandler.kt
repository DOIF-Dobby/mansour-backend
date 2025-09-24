package org.mj.mansour.marketdata.event

import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.mj.mansour.marketdata.service.StockSubscriptionService
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.kafka.DebeziumMessageParser
import org.mj.mansour.system.kafka.parsePayload
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class InterestAssetAddedEventHandler(
    private val debeziumMessageParser: DebeziumMessageParser,
    private val stockSubscriptionService: StockSubscriptionService,
) {

    @KafkaListener(topics = [InterestAssetAddedEvent.TOPIC])
    fun handle(@Payload message: String) {
        val payload = debeziumMessageParser.parsePayload<InterestAssetAddedEvent.Payload>(rawMessage = message)
        log.info { "handle InterestAssetAddedEvent. Received payload: $payload" }

        // TODO: 구독 실패 시, Exception 잡아서 SAGA 패턴으로 사용자 관심 주식 추가 보상 트랜잭션 처리
        stockSubscriptionService.subscribe(
            symbol = payload.assetSymbol,
            userId = payload.userId,
            market = payload.market,
        )
    }
}

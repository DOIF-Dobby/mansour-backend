package org.mj.mansour.activity.event

import org.mj.mansour.activity.service.InterestAssetService
import org.mj.mansour.contract.marketdata.UndoAddInterestAssetCommand
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.kafka.DebeziumMessageParser
import org.mj.mansour.system.kafka.parsePayload
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UndoAddInterestAssetCommandHandler(
    private val debeziumMessageParser: DebeziumMessageParser,
    private val interestAssetService: InterestAssetService,
) {

    /**
     * UndoAddInterestAssetCommand을 처리합니다.
     * 이 명령은 관심 자산 추가를 취소하는 역할을 합니다.
     */
    @KafkaListener(topics = [UndoAddInterestAssetCommand.TOPIC])
    fun handle(@Payload message: String) {
        val payload = debeziumMessageParser.parsePayload<UndoAddInterestAssetCommand.Payload>(rawMessage = message)
        log.info { "handle UndoAddInterestAssetCommand. Received payload: $payload" }

        interestAssetService.compensateAddInterestAsset(
            groupId = payload.groupId,
            assetId = payload.assetId
        )
    }
}

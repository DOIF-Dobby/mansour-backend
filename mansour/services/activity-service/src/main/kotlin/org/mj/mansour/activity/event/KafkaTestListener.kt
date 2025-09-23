package org.mj.mansour.activity.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class KafkaTestListener(
    private val objectMapper: ObjectMapper,
) {

    @KafkaListener(topics = ["outbox.event.InterestAsset.InterestAssetAddedEvent"])
    fun handleTest(@Header(KafkaHeaders.RECEIVED_KEY) key: String, @Payload message: String) {
        println("key = $key")
        println("Original raw message = $message")

        // 1. message는 JSON 객체가 아니라, JSON 형태의 '문자열'이므로
        // 먼저 String 클래스로 변환하여 겉따옴표를 벗겨내고 안쪽 내용물만 꺼냅니다.
        val innerJsonString = objectMapper.readValue(message, String::class.java)
        println("innerJsonString = ${innerJsonString}")

        // 2. 이제 순수한 JSON 문자열이 되었으므로, 최종 DTO로 변환합니다.
        val eventDto = objectMapper.readValue(innerJsonString, InterestAssetAddedEvent.Payload::class.java)

        println("Successfully parsed DTO = $eventDto")
        println("dto to string = ${objectMapper.writeValueAsString(eventDto)}")

    }
}

package org.mj.mansour.activity.event

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaTestListener {

    @KafkaListener(topics = ["outbox.event.InterestAsset"])
    fun handleTest(message: String) {
        println("message = ${message}")
    }
}

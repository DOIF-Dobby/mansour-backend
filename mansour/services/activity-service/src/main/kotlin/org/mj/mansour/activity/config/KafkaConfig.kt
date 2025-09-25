package org.mj.mansour.activity.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.system.json.DebeziumMessageParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun debeziumMessageParser(objectMapper: ObjectMapper): DebeziumMessageParser {
        return DebeziumMessageParser(objectMapper)
    }
}

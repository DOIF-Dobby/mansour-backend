package org.mj.mansour.marketdata.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.system.kafka.DebeziumMessageParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun debeziumMessageParser(objectMapper: ObjectMapper): DebeziumMessageParser {
        return DebeziumMessageParser(objectMapper)
    }
}

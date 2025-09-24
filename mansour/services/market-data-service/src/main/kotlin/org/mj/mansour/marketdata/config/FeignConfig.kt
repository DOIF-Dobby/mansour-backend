package org.mj.mansour.marketdata.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.system.feign.FeignClientExecutor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun feignClientExecutor(objectMapper: ObjectMapper): FeignClientExecutor {
        return FeignClientExecutor(objectMapper)
    }
}

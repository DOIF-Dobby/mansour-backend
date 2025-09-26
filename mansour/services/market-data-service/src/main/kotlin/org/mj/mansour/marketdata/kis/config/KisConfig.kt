package org.mj.mansour.marketdata.kis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class KisConfig(
    private val kisProperties: KisProperties
) {

    @Bean
    fun kisRestClient(): RestClient {
        return RestClient.builder()
            .baseUrl(kisProperties.url)
            .defaultHeaders {
                it.set("Content-Type", "application/json")
                it.set("appkey", kisProperties.appKey)
                it.set("appsecret", kisProperties.appSecret)
            }
            .build()
    }
}

package org.mj.mansour.markethistory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@ConfigurationPropertiesScan
@EnableDiscoveryClient
@SpringBootApplication
class MarketHistoryApplication

fun main(args: Array<String>) {
    runApplication<MarketHistoryApplication>(*args)
}

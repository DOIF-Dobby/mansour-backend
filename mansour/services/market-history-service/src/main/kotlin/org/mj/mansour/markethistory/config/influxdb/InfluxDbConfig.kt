package org.mj.mansour.markethistory.config.influxdb

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfluxDbConfig {

    @Bean(destroyMethod = "close")
    fun influxDBClient(properties: InfluxDbProperties): InfluxDBClient {
        return InfluxDBClientFactory.create(
            properties.url,
            properties.token.toCharArray(),
            properties.org,
            properties.bucket
        )
    }
}

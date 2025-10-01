package org.mj.mansour.markethistory.config.influxdb

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("influxdb")
data class InfluxDbProperties(
    val url: String,
    val token: String,
    val org: String,
    val bucket: String
) {
    override fun toString(): String {
        return "InfluxDbProperties(url='$url', token='***', org='$org', bucket='$bucket')"
    }
}

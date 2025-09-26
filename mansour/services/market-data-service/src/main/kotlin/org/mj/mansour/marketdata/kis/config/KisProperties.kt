package org.mj.mansour.marketdata.kis.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kis")
data class KisProperties(
    val url: String,
    val wsUrl: String,
    val appKey: String,
    val appSecret: String,
)

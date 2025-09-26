package org.mj.mansour.marketdata.kis.dto.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class KisRealtimeTokenRequest(
    @param:JsonProperty("grant_type")
    val grantType: String = "client_credentials",

    @param:JsonProperty("appkey")
    val appKey: String,

    @param:JsonProperty("secretkey")
    val secretKey: String
)

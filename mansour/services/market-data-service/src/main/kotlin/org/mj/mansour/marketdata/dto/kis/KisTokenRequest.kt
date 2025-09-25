package org.mj.mansour.marketdata.dto.kis

import com.fasterxml.jackson.annotation.JsonProperty

data class KisTokenRequest(
    @param:JsonProperty("grant_type")
    val grantType: String = "client_credentials",

    @param:JsonProperty("appkey")
    val appKey: String,
    
    @param:JsonProperty("appsecret")
    val appSecret: String
)

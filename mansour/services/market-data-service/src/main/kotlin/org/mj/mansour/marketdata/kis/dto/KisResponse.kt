package org.mj.mansour.marketdata.kis.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KisResponse<T>(
    @param:JsonProperty("output")
    val output: T,

    @param:JsonProperty("rt_cd")
    val returnCode: String,

    @param:JsonProperty("msg_cd")
    val messageCode: String,

    @param:JsonProperty("msg1")
    val message: String
)

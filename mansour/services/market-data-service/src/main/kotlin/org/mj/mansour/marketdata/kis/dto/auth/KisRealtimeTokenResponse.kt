package org.mj.mansour.marketdata.kis.dto.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class KisRealtimeTokenResponse(
    @param:JsonProperty("approval_key")
    val approvalKey: String,
)

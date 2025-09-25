package org.mj.mansour.marketdata.dto.kis

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class KisTokenResponse(
    @param:JsonProperty("access_token")
    val accessToken: String,

    @param:JsonProperty("access_token_token_expired")
    @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val accessTokenTokenExpired: LocalDateTime,

    @param:JsonProperty("token_type")
    val tokenType: String,

    @param:JsonProperty("expires_in")
    val expiresIn: Long
)

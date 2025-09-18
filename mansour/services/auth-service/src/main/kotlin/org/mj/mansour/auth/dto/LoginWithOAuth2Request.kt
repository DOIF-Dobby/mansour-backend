package org.mj.mansour.auth.dto

data class LoginWithOAuth2Request(
    val authorizationCode: String,
)

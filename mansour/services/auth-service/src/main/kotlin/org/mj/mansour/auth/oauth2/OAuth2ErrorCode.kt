package org.mj.mansour.auth.oauth2

enum class OAuth2ErrorCode(
    val code: String,
    val messageProperty: String,
) {
    OAUTH2_AUTHENTICATION_ERROR("OAUTH2_AUTHENTICATION_ERROR", "oauth2.authentication.error"),
}

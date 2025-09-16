package org.mj.mansour.auth.oauth2

import org.mj.mansour.system.api.exception.ApiException
import org.springframework.http.HttpStatus

class OAuth2AuthenticationException : ApiException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = "OAUTH2_AUTHENTICATION_ERROR",
    messageProperty = "oauth2.authentication.error"
)

class OAuth2UnsupportedProviderException(
    providerType: String
) : ApiException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = "OAUTH2_UNSUPPORTED_PROVIDER",
    messageProperty = "oauth2.unsupported.provider",
    messageArguments = arrayOf(providerType)
)

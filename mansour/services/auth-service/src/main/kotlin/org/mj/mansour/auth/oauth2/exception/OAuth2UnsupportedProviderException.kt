package org.mj.mansour.auth.oauth2.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class OAuth2UnsupportedProviderException(
    providerType: String
) : ApiException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = "OAUTH2_UNSUPPORTED_PROVIDER",
    messageProperty = "oauth2.unsupported.provider",
    messageArguments = arrayOf(providerType)
)

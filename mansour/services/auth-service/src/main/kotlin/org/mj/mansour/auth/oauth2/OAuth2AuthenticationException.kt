package org.mj.mansour.auth.oauth2

import org.mj.mansour.system.api.exception.ApiException
import org.springframework.http.HttpStatus

class OAuth2AuthenticationException: ApiException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = OAuth2ErrorCode.OAUTH2_AUTHENTICATION_ERROR.code,
    messageProperty = OAuth2ErrorCode.OAUTH2_AUTHENTICATION_ERROR.messageProperty
)

package org.mj.mansour.auth.oauth2.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class OAuth2AuthenticationException : ApiException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = "OAUTH2_AUTHENTICATION_ERROR",
    messageProperty = "oauth2.authentication.error"
)

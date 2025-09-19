package org.mj.mansour.user.exception

import org.mansour.shared.domain.enums.AuthProvider
import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class DuplicateAuthenticationException(authProvider: AuthProvider) : ApiException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = "DUPLICATE_AUTHENTICATION",
    messageProperty = "user.authentication.duplicate",
    messageArguments = arrayOf(authProvider)
)

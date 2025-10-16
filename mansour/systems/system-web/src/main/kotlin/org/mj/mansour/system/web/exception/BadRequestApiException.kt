package org.mj.mansour.system.web.exception

import org.springframework.http.HttpStatus

abstract class BadRequestApiException(
    code: String,
    message: String = "Bad Request",
    messageProperty: String,
    messageArguments: Array<Any>? = null,
) : ApiException(
    code = code,
    httpStatus = HttpStatus.BAD_REQUEST,
    message = message,
    messageProperty = messageProperty,
    messageArguments = messageArguments,
)

package org.mj.mansour.system.web.exception

import org.springframework.http.HttpStatus

abstract class NotFoundApiException(
    code: String,
    message: String = "Not found",
    messageProperty: String,
    messageArguments: Array<Any>? = null,
) : ApiException(
    code = code,
    httpStatus = HttpStatus.NOT_FOUND,
    message = message,
    messageProperty = messageProperty,
    messageArguments = messageArguments,
)

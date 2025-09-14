package org.mj.mansour.system.api.exception

import org.mj.mansour.system.core.exception.BaseException
import org.springframework.http.HttpStatus

abstract class ApiException(
    val httpStatus: HttpStatus,
    code: String,
    message: String = httpStatus.reasonPhrase,
    messageProperty: String,
    messageArguments: Array<Any>? = null,
) : BaseException(
    code = code,
    message = message,
    messageProperty = messageProperty,
    messageArguments = messageArguments,
)

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


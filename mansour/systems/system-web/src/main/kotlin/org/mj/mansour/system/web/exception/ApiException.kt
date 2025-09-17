package org.mj.mansour.system.web.exception

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



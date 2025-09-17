package org.mj.mansour.system.webmvc.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class UserHeaderNotFoundException : ApiException(
    code = "USER_HEADER_NOT_FOUND",
    httpStatus = HttpStatus.BAD_REQUEST,
    messageProperty = "webmvc.user-header.not-found",
)

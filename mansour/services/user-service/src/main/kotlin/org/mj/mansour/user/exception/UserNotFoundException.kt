package org.mj.mansour.user.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class UserNotFoundException : NotFoundApiException(
    code = "USER_NOT_FOUND",
    messageProperty = "user.not.found",
)

package org.mj.mansour.activity.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class InterestGroupNotFoundException : NotFoundApiException(
    code = "INTEREST_GROUP_NOT_FOUND",
    messageProperty = "interest-group.not-found",
)

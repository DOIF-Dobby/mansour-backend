package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class UserStrategyNotFoundException(
    userStrategyId: Long
) : NotFoundApiException(
    code = "USER_STRATEGY_NOT_FOUND",
    messageProperty = "strategy.user.not.found",
    messageArguments = arrayOf(userStrategyId)
)

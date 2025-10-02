package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class DuplicateActiveStrategyException(
    symbol: String,
    strategyName: String
) : BadRequestApiException(
    code = "DUPLICATE_ACTIVE_STRATEGY",
    messageProperty = "strategy.active.duplicate",
    messageArguments = arrayOf(symbol, strategyName)
)

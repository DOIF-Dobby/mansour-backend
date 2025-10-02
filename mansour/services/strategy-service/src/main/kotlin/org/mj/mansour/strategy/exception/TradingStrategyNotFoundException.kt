package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class TradingStrategyNotFoundException(
    strategyId: String,
) : NotFoundApiException(
    code = "TRADING_STRATEGY_NOT_FOUND",
    messageProperty = "strategy.not.found",
    messageArguments = arrayOf(strategyId)
)

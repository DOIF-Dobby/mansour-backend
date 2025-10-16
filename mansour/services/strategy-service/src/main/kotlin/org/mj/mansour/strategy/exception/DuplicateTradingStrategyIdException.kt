package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class DuplicateTradingStrategyIdException(
    id: String
) : BadRequestApiException(
    code = "DUPLICATE_TRADING_STRATEGY_ID",
    messageProperty = "strategy.id.duplicate",
    messageArguments = arrayOf(id)
)

package org.mj.mansour.strategy.dto

import org.mj.mansour.strategy.domain.TradingStrategyType

data class TradingStrategyResponse(
    val tradingStrategyId: String,
    val name: String,
    val description: String,
    val type: TradingStrategyType,
)

package org.mj.mansour.strategy.dto

import org.mj.mansour.strategy.domain.PositionSizingType
import java.math.BigDecimal

data class CreateUserStrategyRequest(
    val strategyId: String,
    val symbol: String,
    val parameters: Map<String, String>,
    val positionSizingType: PositionSizingType,
    val positionSizingValue: BigDecimal,
)

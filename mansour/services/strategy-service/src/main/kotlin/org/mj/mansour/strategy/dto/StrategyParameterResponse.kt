package org.mj.mansour.strategy.dto

import org.mj.mansour.strategy.domain.ParameterType

data class StrategyParameterResponse(
    val strategyId: String,
    val parameterId: Long,
    val parameterName: String,
    val displayName: String,
    val parameterType: ParameterType,
    val required: Boolean,
    val minValue: String?,
    val maxValue: String?,
)

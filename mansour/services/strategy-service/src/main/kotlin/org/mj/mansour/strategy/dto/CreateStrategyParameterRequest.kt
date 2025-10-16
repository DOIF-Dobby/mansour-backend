package org.mj.mansour.strategy.dto

import org.mj.mansour.strategy.domain.ParameterType

data class CreateStrategyParameterRequest(
    val strategyId: String,
    val parameterName: String,
    val displayName: String,
    val description: String,
    val parameterType: ParameterType,
    val required: Boolean = true,
    val minValue: String? = null,
    val maxValue: String? = null,
)

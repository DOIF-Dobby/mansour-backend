package org.mj.mansour.strategy.service

import org.mj.mansour.strategy.domain.StrategyParameter
import org.mj.mansour.strategy.domain.StrategyParameterRepository
import org.mj.mansour.strategy.domain.TradingStrategyRepository
import org.mj.mansour.strategy.dto.CreateStrategyParameterRequest
import org.mj.mansour.strategy.exception.TradingStrategyNotFoundException
import org.mj.mansour.system.data.extension.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StrategyParameterService(
    private val tradingStrategyRepository: TradingStrategyRepository,
    private val strategyParameterRepository: StrategyParameterRepository,
) {

    @Transactional
    fun createStrategyParameter(request: CreateStrategyParameterRequest) {
        val tradingStrategy = tradingStrategyRepository.findByIdOrNull(request.strategyId)
            ?: throw TradingStrategyNotFoundException(request.strategyId)

        val strategyParameter = StrategyParameter(
            strategy = tradingStrategy,
            parameterName = request.parameterName,
            displayName = request.displayName,
            description = request.description,
            parameterType = request.parameterType,
            required = request.required,
            minValue = request.minValue,
            maxValue = request.maxValue,
        )

        strategyParameterRepository.save(strategyParameter)
    }
}

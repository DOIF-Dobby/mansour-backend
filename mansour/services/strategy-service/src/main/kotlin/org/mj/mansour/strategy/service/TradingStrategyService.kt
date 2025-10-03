package org.mj.mansour.strategy.service

import org.mj.mansour.strategy.domain.TradingStrategy
import org.mj.mansour.strategy.domain.TradingStrategyRepository
import org.mj.mansour.strategy.dto.CreateTradingStrategyRequest
import org.mj.mansour.strategy.dto.TradingStrategyResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradingStrategyService(
    private val tradingStrategyRepository: TradingStrategyRepository
) {

    /**
     * 트레이딩 전략을 생성합니다.
     */
    @Transactional
    fun createTradingStrategy(request: CreateTradingStrategyRequest) {

        val tradingStrategy = TradingStrategy(
            id = request.id,
            name = request.name,
            description = request.description,
            type = request.type,
        )

        tradingStrategyRepository.save(tradingStrategy)
    }

    /**
     * 모든 트레이딩 전략을 조회합니다.
     */
    @Transactional(readOnly = true)
    fun getAllTradingStrategies(): List<TradingStrategyResponse> {
        return tradingStrategyRepository.findAll()
            .map { tradingStrategy ->
                TradingStrategyResponse(
                    tradingStrategyId = tradingStrategy.id,
                    name = tradingStrategy.name,
                    description = tradingStrategy.description,
                    type = tradingStrategy.type
                )
            }
    }
}

package org.mj.mansour.strategy.domain

import org.mj.mansour.system.data.jpa.repository.NoDeleteJpaRepository

interface StrategyParameterRepository : NoDeleteJpaRepository<StrategyParameter, String> {

    fun findAllByStrategy(strategy: TradingStrategy): List<StrategyParameter>
}

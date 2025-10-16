package org.mj.mansour.strategy.domain

import org.mj.mansour.system.data.jpa.repository.NoDeleteJpaRepository

interface TradingStrategyRepository : NoDeleteJpaRepository<TradingStrategy, String> {

}

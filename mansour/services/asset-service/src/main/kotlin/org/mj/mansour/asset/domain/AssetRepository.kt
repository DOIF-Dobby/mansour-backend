package org.mj.mansour.asset.domain

import org.mj.mansour.system.data.jpa.repository.NoDeleteJpaRepository

interface AssetRepository : NoDeleteJpaRepository<Asset, Long> {

    fun findBySymbol(symbol: String): Asset?
}

package org.mj.mansour.asset.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AssetRepository : JpaRepository<Asset, Long> {

    fun findBySymbol(symbol: String): Asset?
}

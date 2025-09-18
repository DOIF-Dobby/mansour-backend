package org.mj.mansour.asset.dto

import org.mj.mansour.asset.domain.MarketType

data class RegisterStockRequest(
    val symbol: String,
    val name: String,
    val market: MarketType
)

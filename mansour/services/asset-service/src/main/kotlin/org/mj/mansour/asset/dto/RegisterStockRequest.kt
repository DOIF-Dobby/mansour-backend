package org.mj.mansour.asset.dto

import org.mansour.shared.domain.enums.MarketType

data class RegisterStockRequest(
    val symbol: String,
    val name: String,
    val market: MarketType
)

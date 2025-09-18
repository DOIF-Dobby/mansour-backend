package org.mj.mansour.contract.asset

import org.mansour.shared.domain.enums.AssetType

interface AssetResponse {
    val id: Long
    val assetType: AssetType
    val symbol: String
    val name: String
}

data class StockResponse(
    override val id: Long,
    override val assetType: AssetType = AssetType.STOCK,
    override val symbol: String,
    override val name: String,
    val market: String
) : AssetResponse


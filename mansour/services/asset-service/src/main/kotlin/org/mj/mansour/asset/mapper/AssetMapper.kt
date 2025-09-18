package org.mj.mansour.asset.mapper

import org.mj.mansour.asset.domain.Asset
import org.mj.mansour.asset.domain.Stock
import org.mj.mansour.asset.exception.AssetNotFoundException
import org.mj.mansour.contract.asset.AssetResponse
import org.mj.mansour.contract.asset.StockResponse

fun Asset.toResponse(): AssetResponse {
    return when (this) {
        is Stock -> StockResponse(
            id = this.id,
            symbol = this.symbol,
            name = this.name,
            market = this.market.name,
        )

        else -> throw AssetNotFoundException()
    }
}

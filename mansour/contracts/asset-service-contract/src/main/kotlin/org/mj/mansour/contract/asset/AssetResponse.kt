package org.mj.mansour.contract.asset

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.mansour.shared.domain.enums.AssetType
import org.mansour.shared.domain.enums.MarketType

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "assetType"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = StockResponse::class, name = "STOCK")
)
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
    val market: MarketType
) : AssetResponse


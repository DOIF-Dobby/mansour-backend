package org.mj.mansour.asset.domain.stock

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.mansour.shared.domain.enums.AssetType
import org.mj.mansour.asset.domain.Asset

@Entity
@DiscriminatorValue("STOCK")
class Stock(
    symbol: String,
    nameKor: String,
    
    @Enumerated(EnumType.STRING)
    val market: MarketType
) : Asset(
    symbol = symbol,
    nameKor = nameKor,
    assetType = AssetType.STOCK
)

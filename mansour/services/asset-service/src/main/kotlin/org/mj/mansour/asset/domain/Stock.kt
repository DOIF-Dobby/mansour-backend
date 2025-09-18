package org.mj.mansour.asset.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.mansour.shared.domain.enums.AssetType

@Entity
@DiscriminatorValue("STOCK")
class Stock(
    symbol: String,
    nameKor: String,

    @Enumerated(EnumType.STRING)
    val market: MarketType
) : Asset(
    symbol = symbol,
    name = nameKor,
    assetType = AssetType.STOCK
)

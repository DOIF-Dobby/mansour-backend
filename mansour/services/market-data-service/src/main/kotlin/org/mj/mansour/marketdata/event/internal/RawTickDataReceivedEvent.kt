package org.mj.mansour.marketdata.event.internal

import org.mj.mansour.marketdata.kis.dto.KisRealtimePriceData

data class RawTickDataReceivedEvent(
    val priceData: KisRealtimePriceData
)

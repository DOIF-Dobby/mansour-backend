package org.mj.mansour.marketdata.kis.socket

import org.mj.mansour.marketdata.kis.dto.KisRealtimeMessage
import org.mj.mansour.marketdata.kis.dto.KisRealtimePriceData
import org.mj.mansour.system.core.logging.log
import org.springframework.stereotype.Component

@Component
class KisDomesticRealtimePriceParser {

    fun parse(rawPayload: String): KisRealtimeMessage? {
        try {
            val parts = rawPayload.split("|")
            if (parts.size < 4) return null

            val isEncrypted = parts[0] != "0"
            val transactionId = parts[1]
            val dataCount = parts[2].toIntOrNull() ?: 0
            if (dataCount == 0) {
                return KisRealtimeMessage(isEncrypted, transactionId, 0, emptyList())
            }

            val allFields = parts[3].split("^")

            // 46개씩 데이터를 묶도록 수정
            val records = allFields.chunked(NUM_OF_FIELDS_PER_RECORD)

            val priceDataList = records.mapNotNull { fields ->
                if (fields.size < NUM_OF_FIELDS_PER_RECORD) {
                    return@mapNotNull null
                }

                KisRealtimePriceData(
                    symbol = fields[0],
                    executionTime = fields[1],
                    currentPrice = fields[2].toBigDecimal(),
                    changeSign = fields[3],
                    changeFromPreviousDay = fields[4].toBigDecimal(),
                    changeRate = fields[5].toBigDecimal(),
                    weightedAveragePrice = fields[6].toBigDecimal(),
                    openingPrice = fields[7].toBigDecimal(),
                    highestPrice = fields[8].toBigDecimal(),
                    lowestPrice = fields[9].toBigDecimal(),
                    askPrice1 = fields[10].toBigDecimal(),
                    bidPrice1 = fields[11].toBigDecimal(),
                    tradeVolume = fields[12].toBigDecimal(),
                    accumulatedVolume = fields[13].toBigDecimal(),
                    accumulatedTradeValue = fields[14].toBigDecimal(),
                    sellExecutionCount = fields[15].toBigDecimal(),
                    buyExecutionCount = fields[16].toBigDecimal(),
                    netBuyExecutionCount = fields[17].toBigDecimal(),
                    tradeStrength = fields[18].toBigDecimal(),
                    totalSellVolume = fields[19].toBigDecimal(),
                    totalBuyVolume = fields[20].toBigDecimal(),
                    executionType = fields[21],
                    buyRatio = fields[22].toBigDecimal(),
                    volumeChangeRateFromPreviousDay = fields[23].toBigDecimal(),
                    openingTime = fields[24],
                    signFromOpen = fields[25],
                    changeFromOpen = fields[26].toBigDecimal(),
                    highestTime = fields[27],
                    signFromHigh = fields[28],
                    changeFromHigh = fields[29].toBigDecimal(),
                    lowestTime = fields[30],
                    signFromLow = fields[31],
                    changeFromLow = fields[32].toBigDecimal(),
                    businessDate = fields[33],
                    newMarketOperationCode = fields[34],
                    tradeHaltYn = fields[35],
                    askRemainingVolume1 = fields[36].toBigDecimal(),
                    bidRemainingVolume1 = fields[37].toBigDecimal(),
                    totalAskRemainingVolume = fields[38].toBigDecimal(),
                    totalBidRemainingVolume = fields[39].toBigDecimal(),
                    volumeTurnoverRate = fields[40].toBigDecimal(),
                    previousDaySameTimeVolume = fields[41].toBigDecimal(),
                    previousDaySameTimeVolumeRate = fields[42].toBigDecimal(),
                    hourClassCode = fields[43],
                    marketTerminationCode = fields[44],
                    staticViStandardPrice = fields[45].toBigDecimal()
                )
            }

            return KisRealtimeMessage(isEncrypted, transactionId, dataCount, priceDataList)
        } catch (e: Exception) {
            log.error(e) { "Failed to parse KIS realtime payload: $rawPayload" }
            return null
        }
    }

    companion object {
        private const val NUM_OF_FIELDS_PER_RECORD = 46
    }

}

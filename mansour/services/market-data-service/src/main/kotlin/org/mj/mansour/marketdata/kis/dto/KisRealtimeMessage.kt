package org.mj.mansour.marketdata.kis.dto

data class KisRealtimeMessage(
    val isEncrypted: Boolean,
    val transactionId: String,
    val dataCount: Int,
    val data: List<KisRealtimePriceData>
)

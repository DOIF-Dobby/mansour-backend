package org.mj.mansour.marketdata.kis.event

/**
 * KIS 실시간 WebSocket 연결이 성공적으로 맺어졌을 때 발행되는 이벤트
 */
data class KisDomesticSocketConnectedEvent(
    val sessionId: String,
)

package org.mj.mansour.marketdata.kis.event

import org.mj.mansour.marketdata.kis.socket.KisDomesticRealtimeWebSocketManager
import org.mj.mansour.marketdata.repository.StockSubscriptionRepository
import org.mj.mansour.system.core.logging.log
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class KisWebSocketEventHandler(
    private val kisDomesticRealtimeWebSocketManager: KisDomesticRealtimeWebSocketManager,
    private val stockSubscriptionRepository: StockSubscriptionRepository,
) {

    /**
     * KIS 실시간 WebSocket 연결이 성공적으로 맺어졌을 때 호출되는 이벤트 리스너입니다.
     * 활성화된 국내 주식에 대해 구독을 시작합니다.
     */
    @EventListener(KisDomesticSocketConnectedEvent::class)
    fun handleKisDomesticSocketConnectedEvent(event: KisDomesticSocketConnectedEvent) {
        log.info { "Handling WebSocketConnectedEvent for session: ${event.sessionId}" }

        val activeDomesticStocks = stockSubscriptionRepository.getActiveDomesticStocks()
        if (activeDomesticStocks.isEmpty()) {
            log.info { "No active domestic stocks to subscribe to." }
            return
        }

        activeDomesticStocks.forEach { stock ->
            log.info { "Subscribing to active domestic stock: $stock" }
            kisDomesticRealtimeWebSocketManager.subscribe(stock)
        }
    }
}

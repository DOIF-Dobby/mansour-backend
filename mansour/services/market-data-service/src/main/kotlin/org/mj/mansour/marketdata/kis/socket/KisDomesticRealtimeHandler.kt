package org.mj.mansour.marketdata.kis.socket

import org.mj.mansour.marketdata.event.internal.RawTickDataReceivedEvent
import org.mj.mansour.marketdata.kis.event.KisDomesticSocketConnectedEvent
import org.mj.mansour.system.core.logging.log
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.PongMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class KisDomesticRealtimeHandler(
    private val manager: KisDomesticRealtimeWebSocketManager,
    private val parser: KisDomesticRealtimePriceParser,
    private val eventPublisher: ApplicationEventPublisher,
) : TextWebSocketHandler() {

    /**
     * 연결이 성공적으로 맺어졌을 때 호출됩니다.
     */
    override fun afterConnectionEstablished(session: WebSocketSession) {
        log.info { "Connection Established! Session ID: ${session.id}" }
        manager.registerSession(session)
        manager.updateLastMessageTime()

        eventPublisher.publishEvent(KisDomesticSocketConnectedEvent(session.id))
    }

    /**
     * 서버로부터 텍스트 메시지를 수신했을 때 호출됩니다.
     */
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload

        // 구독 성공 메시지 처리
        if (payload.contains("SUBSCRIBE SUCCESS")) {
            log.info { "Subscription confirmed." }
            return
        }

        // 실시간 데이터 메세지 외의 JSON 형식으로 시작하는 메시지는 무시합니다
        if (payload.startsWith("{")) {
            return
        }

        val response = parser.parse(payload)

        if (response != null) {
            manager.updateLastMessageTime()
            response.data.forEach { priceData ->
                log.info { "  - Symbol: ${priceData.symbol}, Price: ${priceData.currentPrice}, Time: ${priceData.executionTime}, businessDate: ${priceData.businessDate}, tradeVolume: ${priceData.tradeVolume}" }
                eventPublisher.publishEvent(RawTickDataReceivedEvent(priceData = priceData))
            }
        }
    }

    /**
     * 연결이 종료되었을 때 호출됩니다.
     */
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        log.warn { "Connection Closed. Status: $status. Will attempt to reconnect." }
        manager.unregisterSession()
    }

    /**
     * 통신 중 에러가 발생했을 때 호출됩니다.
     */
    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        log.error { "Error ${exception.message}" }
    }

    /**
     * 서버로부터 Pong 메시지를 수신했을 때 호출됩니다.
     */
    override fun handlePongMessage(session: WebSocketSession, message: PongMessage) {
        log.info { "Received Pong message: ${message.payload}" }
    }
}

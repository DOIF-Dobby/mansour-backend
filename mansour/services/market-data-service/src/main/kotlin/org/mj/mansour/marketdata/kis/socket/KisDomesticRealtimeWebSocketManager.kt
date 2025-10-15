package org.mj.mansour.marketdata.kis.socket

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.mansour.marketdata.kis.config.KisProperties
import org.mj.mansour.marketdata.kis.constant.KisRealtimeTransactionId
import org.mj.mansour.marketdata.kis.constant.KisRealtimeTransactionType
import org.mj.mansour.marketdata.kis.service.KisAuthService
import org.mj.mansour.system.core.logging.log
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.web.socket.PingMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import java.nio.ByteBuffer
import java.time.Duration
import java.time.Instant

@Component
class KisDomesticRealtimeWebSocketManager(
    private val kisProperties: KisProperties,
    private val objectMapper: ObjectMapper,
    private val kisAuthService: KisAuthService,
    private val parser: KisDomesticRealtimePriceParser,
    private val eventPublisher: ApplicationEventPublisher,
    private val webSocketClient: StandardWebSocketClient,
) {

    private var session: WebSocketSession? = null

    val sessionIsOpen: Boolean
        get() = session?.isOpen == true

    @Volatile
    private var lastMessageTime: Instant? = null


    /**
     * WebSocket 서버에 연결을 시도합니다.
     * 연결이 이미 열려 있다면 아무 작업도 하지 않습니다.
     */
    fun connect() {
        if (sessionIsOpen) {
            return
        }

        log.info { "Attempting to connect to KIS WebSocket server..." }
        val handler = KisDomesticRealtimeHandler(manager = this, parser = parser, eventPublisher = eventPublisher)
        webSocketClient.execute(handler, kisProperties.wsUrl)
    }

    /**
     * WebSocket 서버와의 연결을 종료합니다.
     * 연결이 이미 닫혀 있다면 아무 작업도 하지 않습니다.
     */
    fun disconnect() {
        if (!sessionIsOpen) {
            log.warn { "WebSocket session is not open. Nothing to disconnect." }
            return
        }

        try {
            session?.close()
            unregisterSession()
            log.info { "WebSocket session closed successfully." }
        } catch (e: Exception) {
            log.error(e) { "Failed to close WebSocket session." }
        }
    }

    /**
     * 특정 주식에 대한 실시간 데이터를 구독합니다.
     */
    fun subscribe(symbol: String) {
        if (!sessionIsOpen) {
            log.warn { "WebSocket session is not open. Attempting to connect..." }
            return
        }

        try {
            val payload = createSubscriptionPayload(
                transactionType = KisRealtimeTransactionType.SUBSCRIBE,
                symbol = symbol
            )

            session?.sendMessage(TextMessage(payload))
            log.info { "Subscription message sent for symbol: $symbol" }

        } catch (e: Exception) {
            log.error(e) { "Failed to send subscription message for symbol: $symbol" }
        }
    }

    /**
     * 특정 주식에 대한 실시간 데이터 구독을 취소합니다.
     */
    fun unsubscribe(symbol: String) {
        if (!sessionIsOpen) {
            log.warn { "WebSocket session is not open. Cannot unsubscribe." }
            return
        }

        try {
            val payload = createSubscriptionPayload(
                transactionType = KisRealtimeTransactionType.UNSUBSCRIBE,
                symbol = symbol
            )

            session?.sendMessage(TextMessage(payload))
            log.info { "Unsubscription message sent for symbol: $symbol" }

        } catch (e: Exception) {
            log.error(e) { "Failed to send unsubscription message for symbol: $symbol" }
        }
    }

    /**
     * WebSocket 서버에 핑 메시지를 전송합니다.
     */
    fun sendPingMessage() {
        if (!sessionIsOpen) {
            log.warn { "WebSocket session is not open. Cannot send ping message." }
            return
        }

        try {
            val pingPayload = ByteBuffer.wrap("ping".toByteArray())
            session?.sendMessage(PingMessage(pingPayload))
            log.info { "Ping message sent to WebSocket server." }
        } catch (e: Exception) {
            log.error(e) { "Failed to send ping message." }
        }
    }

    /**
     * 마지막 메시지 수신 후 주어진 시간이 지났는지 확인합니다.
     */
    fun isConnectionIdle(idleThreshold: Duration): Boolean {
        val lastTime = this.lastMessageTime ?: return false // 아직 메시지를 받은 적 없으면 false
        val now = Instant.now()
        return Duration.between(lastTime, now) > idleThreshold
    }

    /**
     * 핸들러가 연결 성공 시 호출하여 세션을 저장하는 내부 메서드
     */
    internal fun registerSession(session: WebSocketSession) {
        this.session = session
    }

    /**
     * 핸들러가 연결 종료 시 호출하여 세션을 정리하는 내부 메서드
     */
    internal fun unregisterSession() {
        this.session = null
    }

    /**
     * 핸들러가 메시지를 수신할 때마다 호출하여 마지막 수신 시간을 갱신합니다.
     */
    internal fun updateLastMessageTime() {
        this.lastMessageTime = Instant.now()
    }

    /**
     * 주어진 거래 유형과 심볼에 대한 구독 또는 구독 취소 메시지 페이로드를 생성합니다.
     */
    private fun createSubscriptionPayload(transactionType: KisRealtimeTransactionType, symbol: String): String {
        val payload = mapOf(
            "header" to mapOf(
                "approval_key" to kisAuthService.getRealtimeToken(),
                "custtype" to "P",
                "tr_type" to transactionType.code,
                "content-type" to "utf-8"
            ),
            "body" to mapOf(
                "input" to mapOf(
                    "tr_id" to KisRealtimeTransactionId.DOMESTIC_REALTIME_TRADE_TOTAL,
                    "tr_key" to symbol
                )
            )
        )

        return objectMapper.writeValueAsString(payload)
    }

}



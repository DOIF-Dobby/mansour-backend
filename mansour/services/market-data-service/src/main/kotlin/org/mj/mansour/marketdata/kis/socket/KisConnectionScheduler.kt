package org.mj.mansour.marketdata.kis.socket

import org.mj.mansour.system.core.logging.log
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class KisConnectionScheduler(
    private val webSocketManager: KisDomesticRealtimeWebSocketManager,
) : ApplicationListener<ApplicationReadyEvent> {

    /**
     * 애플리케이션이 시작될 때 KIS WebSocket 연결을 초기화합니다.
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        log.info { "Application started. Initializing KIS WebSocket connection..." }
        webSocketManager.connect()
    }

    /**
     * 매일 오전 7시 59분에 WebSocket 연결을 엽니다.
     * 이 시간은 시장이 열리기 직전으로 설정되어 있습니다.
     */
    @Scheduled(cron = "0 59 7 * * MON-FRI", zone = "Asia/Seoul")
    fun openConnection() {
        log.info { "Scheduled job: Opening KIS WebSocket connection..." }
        webSocketManager.connect()
    }

    /**
     * 매일 오후 8시 1분에 WebSocket 연결을 닫습니다.
     * 이 시간은 시장이 닫힌 직후로 설정되어 있습니다.
     */
    @Scheduled(cron = "0 1 20 * * MON-FRI", zone = "Asia/Seoul")
    fun closeConnection() {
        log.info { "Scheduled job: Closing KIS WebSocket connection..." }
        webSocketManager.disconnect()
    }

    /**
     * 매일 오전 8시부터 오후 8시까지 30초마다 WebSocket 연결 상태를 확인하고,
     * 연결이 닫혀 있다면 재연결을 시도합니다.
     */
    @Scheduled(cron = "*/30 * 8-19 * * MON-FRI", zone = "Asia/Seoul")
    fun checkAndReconnect() {
        if (!webSocketManager.sessionIsOpen) {
            log.info { "Scheduled job: Reconnecting to KIS WebSocket server..." }
            webSocketManager.connect()
        }
    }
}

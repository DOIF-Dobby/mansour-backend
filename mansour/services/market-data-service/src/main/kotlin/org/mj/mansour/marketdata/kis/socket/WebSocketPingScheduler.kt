package org.mj.mansour.marketdata.kis.socket

import org.mj.mansour.system.core.logging.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class WebSocketPingScheduler(
    private val webSocketManager: KisDomesticRealtimeWebSocketManager
) {

    @Scheduled(fixedRate = 10_000)
    fun sendPing() {
        if (webSocketManager.isConnectionIdle(IDLE_THRESHOLD)) {
            log.info { "Connection has been idle for over $IDLE_THRESHOLD. Sending Ping." }
            webSocketManager.sendPingMessage()
        }
    }

    companion object {
        // 마지막 메세지 수신 후 일정 시간 동안 메세지가 수신되지 않으면 Ping을 보냅니다.
        private val IDLE_THRESHOLD = Duration.ofSeconds(20)

    }
}

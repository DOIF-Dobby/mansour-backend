package org.mj.mansour.marketdata.api

import org.mj.mansour.marketdata.kis.socket.KisDomesticRealtimeWebSocketManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val kisDomesticRealtimeWebSocketManager: KisDomesticRealtimeWebSocketManager,
) {

    @GetMapping("/connect")
    fun connect() {
        kisDomesticRealtimeWebSocketManager.connect()
    }

    @GetMapping("/subscribe/{symbol}")
    fun subscribe(@PathVariable symbol: String) {
        kisDomesticRealtimeWebSocketManager.subscribe(symbol)
    }

    @GetMapping("/unsubscribe/{symbol}")
    fun unsubscribe(@PathVariable symbol: String) {
        kisDomesticRealtimeWebSocketManager.unsubscribe(symbol)
    }
}

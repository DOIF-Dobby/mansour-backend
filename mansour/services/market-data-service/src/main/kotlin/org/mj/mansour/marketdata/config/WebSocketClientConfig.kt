package org.mj.mansour.marketdata.config

import jakarta.websocket.WebSocketContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean

@Configuration
class WebSocketClientConfig {

    @Bean
    fun createWebSocketContainer(): WebSocketContainerFactoryBean {
        val container = WebSocketContainerFactoryBean()
        container.maxTextMessageBufferSize = 512 * 1024

        return container
    }

    @Bean
    fun webSocketClient(webSocketContainer: WebSocketContainer): StandardWebSocketClient {
        return StandardWebSocketClient(webSocketContainer)
    }
}

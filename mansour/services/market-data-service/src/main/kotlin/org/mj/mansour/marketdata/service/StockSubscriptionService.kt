package org.mj.mansour.marketdata.service

import org.mansour.shared.domain.enums.MarketType
import org.mj.mansour.marketdata.kis.socket.KisDomesticRealtimeWebSocketManager
import org.mj.mansour.marketdata.repository.StockSubscriptionRepository
import org.springframework.stereotype.Service

@Service
class StockSubscriptionService(
    private val stockSubscriptionRepository: StockSubscriptionRepository,
    private val kisDomesticRealtimeWebSocketManager: KisDomesticRealtimeWebSocketManager,
) {

    /**
     * 사용자가 특정 주식에 구독을 시작합니다.
     */
    fun subscribe(userId: Long, symbol: String, market: MarketType) {
        // 주식별 구독자 Set에 사용자 추가
        val isNewSubscriber = stockSubscriptionRepository.addSubscriber(symbol, market, userId)

        // 새로운 구독자인 경우
        if (isNewSubscriber) {
            // 현재 구독자 수 확인
            val subscriberCount = stockSubscriptionRepository.getSubscriberCount(symbol, market)

            // 이 사용자가 첫 구독자라면, 활성 주식 목록에 추가
            if (subscriberCount == 1L) {
                stockSubscriptionRepository.addActiveStock(symbol, market)
                kisDomesticRealtimeWebSocketManager.subscribe(symbol) // TODO: 더 나은 방법 찾아보기
            }
        }
    }

    /**
     * 사용자가 특정 주식에 대한 구독을 취소합니다.
     */
    fun unsubscribe(symbol: String, userId: Long, market: MarketType) {
        // 1. 주식별 구독자 Set에서 사용자 제거
        val wasSubscriber = stockSubscriptionRepository.removeSubscriber(symbol, market, userId)

        // 2. 실제로 구독자였던 경우에만 로직 실행
        if (wasSubscriber) {
            // 3. 남은 구독자 수 확인
            val subscriberCount = stockSubscriptionRepository.getSubscriberCount(symbol, market)

            // 4. 남은 구독자 수가 0명이라면, 활성 주식 목록에서 제거
            if (subscriberCount == 0L) {
                stockSubscriptionRepository.removeActiveStock(symbol, market)
                kisDomesticRealtimeWebSocketManager.unsubscribe(symbol) // TODO: 더 나은 방법 찾아보기
            }
        }
    }

}

package org.mj.mansour.marketdata.scheduler

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.mj.mansour.contract.marketdata.StockPriceUpdatedEvent
import org.mj.mansour.marketdata.dto.kis.KisDomesticPriceData
import org.mj.mansour.marketdata.redis.MarketDataRedisKeys
import org.mj.mansour.marketdata.service.OutboxService
import org.mj.mansour.marketdata.service.kis.domestic.KisDomesticPriceService
import org.mj.mansour.system.core.logging.log
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MarketDataScheduler(
    private val kisDomesticPriceService: KisDomesticPriceService,
    private val redisTemplate: RedisTemplate<String, String>,
    private val outboxService: OutboxService,
) {

    /**
     * KIS로부터 국내 주식 가격 데이터를 주기적으로 가져옵니다.
     */
    @Scheduled(cron = "0/10 * * * * *")
    @SchedulerLock(name = "fetchDomesticMarketData")
    fun fetchDomesticMarketData() {
        // TODO: KIS API 중에 한번에 여러 종목 데이터를 가져올 수 있는 API가 있다면 해당 API로 변경
        // TODO: 국내 주식이니깐 장 활성 시간에만 실행되도록 변경

        val activeDomesticStocksKey = MarketDataRedisKeys.getActiveDomesticStocksKey()
        val symbols = redisTemplate.opsForSet().members(activeDomesticStocksKey)

        if (symbols.isNullOrEmpty()) {
            return
        }

        symbols.forEach { symbol ->
            try {
                val domesticPrice = kisDomesticPriceService.fetchDomesticPrice(symbol)
                val eventPayload = convertToStockPriceUpdatedEventPayload(domesticPrice)

                outboxService.saveOutboxRecord(
                    aggregateId = eventPayload.symbol,
                    aggregateType = StockPriceUpdatedEvent.AGGREGATE_TYPE,
                    eventType = StockPriceUpdatedEvent.EVENT_TYPE,
                    payload = eventPayload
                )
            } catch (e: Exception) {
                log.error { "Error fetching price for $symbol: ${e.message}" }
            }
        }
    }

    fun convertToStockPriceUpdatedEventPayload(priceData: KisDomesticPriceData): StockPriceUpdatedEvent.Payload {
        return StockPriceUpdatedEvent.Payload(
            timestamp = Instant.now(),
            symbol = priceData.stockShortenedCode,
            open = priceData.openingPrice,
            high = priceData.highestPrice,
            low = priceData.lowestPrice,
            close = priceData.stockPrice,
            volume = priceData.accumulatedVolume
        )
    }
}

package org.mj.mansour.markethistory.api

import org.mj.mansour.markethistory.dto.CandleResponse
import org.mj.mansour.markethistory.repository.StockPriceRepository
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.ContentApiResponse
import org.mj.mansour.system.web.response.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val stockPriceRepository: StockPriceRepository,
) {

    @GetMapping("/stock/recent/{symbol}")
    fun getRecentStocks(@PathVariable symbol: String, @RequestParam limit: Int): ContentApiResponse<CandleResponse> {
        val findRecentCandles = stockPriceRepository.findRecentCandles(symbol = symbol, limit = limit)
            .map { candle ->
                CandleResponse(
                    time = candle.windowStartTime.epochSecond,
                    symbol = candle.symbol,
                    open = candle.open,
                    high = candle.high,
                    low = candle.low,
                    close = candle.close,
                    volume = candle.volume,
                )
            }

        return ApiResponse.ok(content = findRecentCandles)
    }
}

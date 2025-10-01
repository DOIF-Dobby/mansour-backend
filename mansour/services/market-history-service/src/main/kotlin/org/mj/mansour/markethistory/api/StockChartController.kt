package org.mj.mansour.markethistory.api

import org.mj.mansour.markethistory.dto.CandleResponse
import org.mj.mansour.markethistory.dto.CandleSearchCondition
import org.mj.mansour.markethistory.repository.StockChartRepository
import org.mj.mansour.system.web.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class StockChartController(
    private val stockChartRepository: StockChartRepository,
) {

    @GetMapping("/stock/chart")
    fun getStockChart(@ModelAttribute condition: CandleSearchCondition): ApiResponse<List<CandleResponse>> {
        val findCandles = stockChartRepository.findCandles(
            symbol = condition.symbol,
            resolution = condition.resolution,
            from = condition.from,
            to = condition.to,
        ).map { candle ->
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

        return ApiResponse.ok(data = findCandles)
    }
}

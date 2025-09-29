package org.mj.mansour.markethistory.api

import org.mj.mansour.markethistory.domain.StockCandle
import org.mj.mansour.markethistory.repository.StockPriceRepository
import org.mj.mansour.system.web.response.ApiResponse
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.Instant

@Profile("local")
@RestController
class TestController(
    private val stockPriceRepository: StockPriceRepository,
) {

    @PostMapping("/test")
    fun test() {
        stockPriceRepository.save(
            StockCandle(
                symbol = "005930",
                open = BigDecimal.valueOf(84400),
                high = BigDecimal.valueOf(84400),
                low = BigDecimal.valueOf(84400),
                close = BigDecimal.valueOf(84400),
                volume = BigDecimal.valueOf(84400),
                windowStartTime = Instant.ofEpochSecond(1759135440),
                windowEndTime = Instant.ofEpochSecond(1759135500)
            )
        )
    }

    @GetMapping("/test2/{symbol}")
    fun test2(@PathVariable("symbol") symbol: String): ApiResponse<List<StockCandle>> {
        val findRecentCandles = stockPriceRepository.findRecentCandles(symbol = symbol)
        return ApiResponse.ok(data = findRecentCandles)
    }
}

package org.mj.mansour.strategy.api

import org.mj.mansour.strategy.dto.CreateTradingStrategyRequest
import org.mj.mansour.strategy.dto.TradingStrategyResponse
import org.mj.mansour.strategy.service.TradingStrategyService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.ContentApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.web.response.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradingStrategyController(
    private val tradingStrategyService: TradingStrategyService,
) {

    /**
     * 트레이딩 전략을 생성합니다.
     */
    @PostMapping
    fun createTradingStrategy(@RequestBody request: CreateTradingStrategyRequest): UnitApiResponse {
        tradingStrategyService.createTradingStrategy(request)

        return ApiResponse.ok()
    }

    /**
     * 모든 트레이딩 전략을 조회합니다.
     */
    @GetMapping
    fun getAllTradingStrategies(): ContentApiResponse<TradingStrategyResponse> {
        val allTradingStrategies = tradingStrategyService.getAllTradingStrategies()

        return ApiResponse.ok(content = allTradingStrategies)
    }
}

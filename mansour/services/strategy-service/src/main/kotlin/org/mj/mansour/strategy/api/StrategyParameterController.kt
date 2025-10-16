package org.mj.mansour.strategy.api

import org.mj.mansour.strategy.dto.CreateStrategyParameterRequest
import org.mj.mansour.strategy.dto.StrategyParameterResponse
import org.mj.mansour.strategy.service.StrategyParameterService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.ContentApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.web.response.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StrategyParameterController(
    private val strategyParameterService: StrategyParameterService,
) {

    /**
     * 트레이딩 전략 파라미터를 생성합니다.
     */
    @PostMapping("/parameters")
    fun createTradingStrategyParameters(@RequestBody request: CreateStrategyParameterRequest): UnitApiResponse {
        strategyParameterService.createStrategyParameter(request)

        return ApiResponse.ok()
    }

    /**
     * 특정 트레이딩 전략에 대한 파라미터를 조회합니다.
     */
    @GetMapping("/parameters")
    fun getParameters(@RequestParam("strategyId") strategyId: String): ContentApiResponse<StrategyParameterResponse> {
        val parameters = strategyParameterService.getParametersByStrategy(strategyId)

        return ApiResponse.ok(content = parameters)
    }
}

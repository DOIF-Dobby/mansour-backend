package org.mj.mansour.strategy.api

import org.mj.mansour.strategy.dto.CreateStrategyParameterRequest
import org.mj.mansour.strategy.dto.CreateTradingStrategyRequest
import org.mj.mansour.strategy.dto.CreateUserStrategyRequest
import org.mj.mansour.strategy.service.StrategyParameterService
import org.mj.mansour.strategy.service.TradingStrategyService
import org.mj.mansour.strategy.service.UserStrategyService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.util.ApiHeaderUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradingStrategyController(
    private val tradingStrategyService: TradingStrategyService,
    private val strategyParameterService: StrategyParameterService,
    private val userStrategyService: UserStrategyService,
) {

    @PostMapping
    fun createTradingStrategy(@RequestBody request: CreateTradingStrategyRequest): UnitApiResponse {
        tradingStrategyService.createTradingStrategy(request)

        return ApiResponse.ok()
    }

    @PostMapping("/parameters")
    fun createTradingStrategyParameters(@RequestBody request: CreateStrategyParameterRequest): UnitApiResponse {
        strategyParameterService.createStrategyParameter(request)

        return ApiResponse.ok()
    }

    @PostMapping("/user-strategies")
    fun createUserStrategy(@RequestBody request: CreateUserStrategyRequest): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.createUserStrategy(userId = currentUserId, request = request)

        return ApiResponse.ok()
    }

    @PutMapping("/user-strategies/{userStrategyId}/activate")
    fun activateUserStrategy(@PathVariable userStrategyId: Long): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.activateUserStrategy(userId = currentUserId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }

    @PutMapping("/user-strategies/{userStrategyId}/deactivate")
    fun deactivateUserStrategy(@PathVariable userStrategyId: Long): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.deactivateUserStrategy(userId = currentUserId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }
}

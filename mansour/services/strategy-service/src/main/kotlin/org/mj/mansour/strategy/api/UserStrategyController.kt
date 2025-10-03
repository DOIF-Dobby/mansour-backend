package org.mj.mansour.strategy.api

import org.mj.mansour.strategy.dto.CreateUserStrategyRequest
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
class UserStrategyController(
    private val userStrategyService: UserStrategyService,
) {

    /**
     * 사용자 트레이딩 전략을 생성합니다.
     */
    @PostMapping("/user-strategies")
    fun createUserStrategy(@RequestBody request: CreateUserStrategyRequest): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.createUserStrategy(userId = currentUserId, request = request)

        return ApiResponse.ok()
    }

    /**
     * 사용자 트레이딩 전략을 활성화합니다.
     */
    @PutMapping("/user-strategies/{userStrategyId}/activate")
    fun activateUserStrategy(@PathVariable userStrategyId: Long): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.activateUserStrategy(userId = currentUserId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }

    /**
     * 사용자 트레이딩 전략을 비활성화합니다.
     */
    @PutMapping("/user-strategies/{userStrategyId}/deactivate")
    fun deactivateUserStrategy(@PathVariable userStrategyId: Long): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        userStrategyService.deactivateUserStrategy(userId = currentUserId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }

}

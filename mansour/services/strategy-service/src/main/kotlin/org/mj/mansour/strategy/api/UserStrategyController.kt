package org.mj.mansour.strategy.api

import org.mj.mansour.strategy.dto.CreateUserStrategyRequest
import org.mj.mansour.strategy.service.UserStrategyService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.resolver.UserId
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
    fun createUserStrategy(@UserId userId: Long, @RequestBody request: CreateUserStrategyRequest): UnitApiResponse {
        userStrategyService.createUserStrategy(userId = userId, request = request)

        return ApiResponse.ok()
    }

    /**
     * 사용자 트레이딩 전략을 활성화합니다.
     */
    @PutMapping("/user-strategies/{userStrategyId}/activate")
    fun activateUserStrategy(@UserId userId: Long, @PathVariable userStrategyId: Long): UnitApiResponse {
        userStrategyService.activateUserStrategy(userId = userId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }

    /**
     * 사용자 트레이딩 전략을 비활성화합니다.
     */
    @PutMapping("/user-strategies/{userStrategyId}/deactivate")
    fun deactivateUserStrategy(@UserId userId: Long, @PathVariable userStrategyId: Long): UnitApiResponse {
        userStrategyService.deactivateUserStrategy(userId = userId, userStrategyId = userStrategyId)

        return ApiResponse.ok()
    }

}

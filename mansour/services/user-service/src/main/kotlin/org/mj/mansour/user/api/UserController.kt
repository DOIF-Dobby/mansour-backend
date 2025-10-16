package org.mj.mansour.user.api

import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.resolver.UserId
import org.mj.mansour.user.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/hello")
    fun hello(@UserId userId: Long): UnitApiResponse {
        log.info { "currentUserId = $userId" }

        return ApiResponse.ok()
    }

    @GetMapping
    fun getUser(@UserId userId: Long): ApiResponse<UserResponse> {
        val user = userService.getUser(userId = userId)
        return ApiResponse.ok(data = user)
    }

    @DeleteMapping
    fun deleteUser(@UserId userId: Long): UnitApiResponse {
        userService.deleteUser(userId = userId)
        return ApiResponse.ok()
    }
}

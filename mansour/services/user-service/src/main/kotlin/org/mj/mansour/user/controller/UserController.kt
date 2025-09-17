package org.mj.mansour.user.controller

import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.util.ApiHeaderUtils
import org.mj.mansour.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/hello")
    fun hello(): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        log.info { "currentUserId = $currentUserId" }

        return ApiResponse.ok()
    }

    @GetMapping
    fun getUser(): ApiResponse<UserResponse> {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        val user = userService.getUser(userId = currentUserId)

        return ApiResponse.ok(data = user)
    }
}

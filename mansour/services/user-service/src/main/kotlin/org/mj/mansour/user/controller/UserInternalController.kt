package org.mj.mansour.user.controller

import org.mj.mansour.contract.user.FindOrCreateUser
import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.user.service.UserInternalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/api/users")
class UserInternalController(
    private val userInternalService: UserInternalService
) {

    @PostMapping("/find-or-create")
    fun findOrCreateUser(@RequestBody request: FindOrCreateUser): ApiResponse<UserResponse> {
        val userResponse = userInternalService.findOrCreateUser(request)
        return ApiResponse.ok(data = userResponse)
    }

    @GetMapping("/test")
    fun test(): UnitApiResponse {
        return ApiResponse.ok()
    }
}

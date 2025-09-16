package org.mj.mansour.auth.client

import org.mj.mansour.contract.user.FindOrCreateUser
import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.system.api.response.ApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "user-service")
interface UserServiceClient {

    @PostMapping("/internal/api/users/find-or-create")
    fun findOrCreateUser(@RequestBody request: FindOrCreateUser): ApiResponse<UserResponse>
}

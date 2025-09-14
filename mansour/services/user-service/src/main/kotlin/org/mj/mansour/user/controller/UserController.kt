package org.mj.mansour.user.controller

import org.mj.mansour.system.api.response.ApiResponse
import org.mj.mansour.system.api.response.UnitApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/hello")
    fun hello(): UnitApiResponse {
        return ApiResponse.ok()
    }
}

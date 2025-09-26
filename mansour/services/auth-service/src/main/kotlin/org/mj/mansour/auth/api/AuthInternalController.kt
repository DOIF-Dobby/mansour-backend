package org.mj.mansour.auth.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/internal/api/auth")
class AuthInternalController {

    @GetMapping("/test")
    fun test(): String {
        return "Auth Internal Test Endpoint"
    }
}

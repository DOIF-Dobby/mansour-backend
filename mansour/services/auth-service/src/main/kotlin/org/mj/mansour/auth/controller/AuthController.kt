package org.mj.mansour.auth.controller

import org.mj.mansour.auth.jwt.JwtTokenProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider
) {

    @GetMapping("/hello")
    fun hello(): String {
        val generateToken = jwtTokenProvider.generateToken("123")
        return "Hello, JWT Token: $generateToken"
    }

}

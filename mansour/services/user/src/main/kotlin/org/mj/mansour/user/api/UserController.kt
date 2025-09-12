package org.mj.mansour.user.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, Mansour User Service!"
    }
}

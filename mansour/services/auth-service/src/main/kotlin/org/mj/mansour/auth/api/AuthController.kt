package org.mj.mansour.auth.api

import org.mj.mansour.auth.dto.LoginWithOAuth2Request
import org.mj.mansour.auth.oauth2.OAuth2RedirectUrlResolver
import org.mj.mansour.auth.service.AuthService
import org.mj.mansour.system.web.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val oAuth2RedirectUrlResolver: OAuth2RedirectUrlResolver,
    private val authService: AuthService,
) {

    @GetMapping("/url/{provider}")
    fun getOAuth2RedirectUrl(@PathVariable provider: String): ApiResponse<String> {
        val resolveRedirectUrl = oAuth2RedirectUrlResolver.resolveRedirectUrl(provider)
        return ApiResponse.ok(data = resolveRedirectUrl)
    }

    @PostMapping("/login/{provider}")
    fun loginWithOAuth2(@PathVariable provider: String, @RequestBody request: LoginWithOAuth2Request): ApiResponse<String> {
        val token = authService.loginWithOAuth2(provider, request.authorizationCode)
        return ApiResponse.ok(data = token)
    }
}


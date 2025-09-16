package org.mj.mansour.auth.oauth2

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.stereotype.Component

@Component
class OAuth2UserInfoFetcher {

    private val userService = DefaultOAuth2UserService()

    fun fetch(clientRegistration: ClientRegistration, accessToken: OAuth2AccessToken): Map<String, Any> {
        val userRequest = OAuth2UserRequest(clientRegistration, accessToken)
        val oAuth2User = userService.loadUser(userRequest)

        return oAuth2User.attributes
    }

}

package org.mj.mansour.auth.oauth2

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository

/**
 * 주어진 OAuth2 제공자에 대한 ClientRegistration을 찾습니다.
 */
fun findClientRegistration(
    clientRegistrationRepository: ClientRegistrationRepository,
    provider: String
): ClientRegistration {
    return clientRegistrationRepository.findByRegistrationId(provider)
        ?: throw OAuth2UnsupportedProviderException(provider)
}

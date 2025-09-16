package org.mj.mansour.user.service

import org.mansour.shared.domain.Email
import org.mj.mansour.contract.user.FindOrCreateUser
import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.user.domain.User
import org.mj.mansour.user.domain.UserAuthentication
import org.mj.mansour.user.domain.UserAuthenticationRepository
import org.mj.mansour.user.domain.UserRepository
import org.mj.mansour.user.mapper.toResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInternalService(
    private val userRepository: UserRepository,
    private val userAuthenticationRepository: UserAuthenticationRepository,
) {

    /**
     * 사용자를 찾거나 생성합니다.
     */
    @Transactional
    fun findOrCreateUser(payload: FindOrCreateUser): UserResponse {
        val authentication = userAuthenticationRepository.findByProviderAndProviderId(payload.provider, payload.providerId)

        val userAuthentication = if (authentication != null) {
            authentication
        } else {
            val newUser = User(email = Email(payload.email), username = payload.username)
            userRepository.save(newUser)

            val userAuthentication = UserAuthentication(
                user = newUser,
                provider = payload.provider,
                providerId = payload.providerId
            )
            userAuthenticationRepository.save(userAuthentication)
        }

        return userAuthentication.toResponse()
    }
}

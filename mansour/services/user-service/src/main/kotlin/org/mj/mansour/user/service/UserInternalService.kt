package org.mj.mansour.user.service

import org.mansour.shared.domain.Email
import org.mj.mansour.contract.user.FindOrCreateUserRequest
import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.user.domain.User
import org.mj.mansour.user.domain.UserRepository
import org.mj.mansour.user.mapper.toResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInternalService(
    private val userRepository: UserRepository,
) {

    /**
     * 사용자를 찾거나 생성합니다.
     */
    @Transactional
    fun findOrCreateUser(payload: FindOrCreateUserRequest): UserResponse {
        val findUser = userRepository.findByEmailWithAuthentications(payload.email)

        val user = if (findUser != null) {
            findUser
        } else {
            val newUser = User(email = Email(payload.email), username = payload.username)
            newUser.addAuthentication(provider = payload.provider, providerId = payload.providerId)
            userRepository.save(newUser)
        }

        return user.toResponse()
    }
}

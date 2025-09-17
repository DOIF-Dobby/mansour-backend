package org.mj.mansour.user.service

import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.user.domain.UserRepository
import org.mj.mansour.user.exception.UserNotFoundException
import org.mj.mansour.user.mapper.toResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdWithAuthentications(userId) ?: throw UserNotFoundException()
        return user.toResponse()
    }

    @Transactional
    fun deleteUser(userId: Long) {
        val user = userRepository.findByIdWithAuthentications(userId) ?: throw UserNotFoundException()
        user.delete()
    }
}

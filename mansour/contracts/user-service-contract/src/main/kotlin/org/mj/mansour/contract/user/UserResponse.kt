package org.mj.mansour.contract.user

import org.mansour.shared.domain.AuthProvider

data class UserResponse(
    val userId: Long,
    val email: String,
    val username: String,
    val provider: AuthProvider,
    val providerId: String,
)

package org.mj.mansour.contract.user

import org.mansour.shared.domain.AuthProvider

data class UserResponse(
    val userId: Long,
    val email: String,
    val username: String,
    val authentications: List<UserAuthenticationResponse>
)

data class UserAuthenticationResponse(
    val provider: AuthProvider,
    val providerId: String,
)

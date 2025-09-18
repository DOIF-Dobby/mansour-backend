package org.mj.mansour.contract.user

import org.mansour.shared.domain.enums.AuthProvider

data class FindOrCreateUserRequest(
    val email: String,
    val username: String,
    val provider: AuthProvider,
    val providerId: String,
)

package org.mj.mansour.contract.user

import org.mansour.shared.domain.enums.AuthProvider

data class FindOrCreateUser(
    val email: String,
    val username: String,
    val provider: AuthProvider,
    val providerId: String,
)

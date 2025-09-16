package org.mj.mansour.user.mapper

import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.user.domain.UserAuthentication

fun UserAuthentication.toResponse(): UserResponse {
    return UserResponse(
        userId = user.id,
        email = user.email.address,
        username = user.username,
        provider = provider,
        providerId = providerId
    )
}

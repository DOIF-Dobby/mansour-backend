package org.mj.mansour.user.mapper

import org.mj.mansour.contract.user.UserAuthenticationResponse
import org.mj.mansour.contract.user.UserResponse
import org.mj.mansour.user.domain.User
import org.mj.mansour.user.domain.UserAuthentication

fun User.toResponse(): UserResponse {
    return UserResponse(
        userId = this.id,
        email = this.email.address,
        username = this.username,
        authentications = this.authentications.map { it.toResponse() }
    )
}

fun UserAuthentication.toResponse(): UserAuthenticationResponse {
    return UserAuthenticationResponse(
        provider = this.provider,
        providerId = this.providerId
    )
}

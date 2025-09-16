package org.mj.mansour.user.domain

import org.mansour.shared.domain.AuthProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserAuthenticationRepository : JpaRepository<UserAuthentication, Long> {

    @Query(
        value = """
        select ua
        from UserAuthentication ua
        join fetch ua.user
        where ua.provider = :provider
        and ua.providerId = :providerId
    """
    )
    fun findByProviderAndProviderId(provider: AuthProvider, providerId: String): UserAuthentication?
}

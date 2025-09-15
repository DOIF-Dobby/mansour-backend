package org.mj.mansour.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthenticationRepository : JpaRepository<UserAuthentication, Long> {
}

package org.mj.mansour.user.domain

import org.mj.mansour.system.data.jpa.repository.NoDeleteJpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : NoDeleteJpaRepository<User, Long> {

    @Query(
        value = """
        select distinct u
        from User u
        left join fetch u.authentications a
        where u.email.address = :email
        and u.deleted = false
    """
    )
    fun findByEmailWithAuthentications(email: String): User?

    @Query(
        value = """
        select distinct u
        from User u
        left join fetch u.authentications a
        where u.id = :userId
        and u.deleted = false
    """
    )
    fun findByIdWithAuthentications(userId: Long): User?
}

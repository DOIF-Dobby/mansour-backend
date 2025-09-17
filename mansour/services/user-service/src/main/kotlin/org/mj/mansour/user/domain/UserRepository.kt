package org.mj.mansour.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    @Query(
        value = """
        select u
        from User u
        left join fetch u.authentications a
        where u.email.address = :email
    """
    )
    fun findByEmailWithAuthentications(email: String): User?

    @Query(
        value = """
        select u
        from User u
        left join fetch u.authentications a
        where u.id = :userId
    """
    )
    fun findByIdWithAuthentications(userId: Long): User?
}

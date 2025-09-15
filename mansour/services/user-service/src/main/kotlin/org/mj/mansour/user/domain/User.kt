package org.mj.mansour.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.mansour.shared.domain.Email

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val email: Email,

    @Column(nullable = false)
    var username: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

}

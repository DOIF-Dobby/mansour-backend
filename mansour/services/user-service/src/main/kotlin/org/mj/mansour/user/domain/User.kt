package org.mj.mansour.user.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.mansour.shared.domain.Email
import org.mansour.shared.domain.enums.AuthProvider
import org.mj.mansour.system.data.jpa.BaseDeletedEntity

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val email: Email,

    @Column(nullable = false)
    var username: String,
) : BaseDeletedEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0L

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authentications: MutableList<UserAuthentication> = mutableListOf()

    /**
     * 사용자 인증 정보를 추가합니다.
     *
     * @param provider 인증 제공자
     * @param providerId 인증 제공자의 ID
     */
    fun addAuthentication(provider: AuthProvider, providerId: String) {
        val authentication = UserAuthentication(
            user = this,
            provider = provider,
            providerId = providerId
        )
        authentications.add(authentication)
    }

}

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
import org.mj.mansour.user.exception.DuplicateAuthenticationException

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val email: Email,

    @Column(nullable = false)
    var username: String
) : BaseDeletedEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0L

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _authentications: MutableList<UserAuthentication> = mutableListOf()

    val authentications: List<UserAuthentication>
        get() = _authentications.toList()

    /**
     * 사용자 인증 정보를 추가합니다.
     *
     * @param provider 인증 제공자
     * @param providerId 인증 제공자의 ID
     */
    fun addAuthentication(provider: AuthProvider, providerId: String) {
        // 이미 해당 제공자에 대한 인증 정보가 있는지 확인
        if (_authentications.any { it.provider == provider }) {
            throw DuplicateAuthenticationException(provider)
        }

        val authentication = UserAuthentication(
            user = this,
            provider = provider,
            providerId = providerId
        )
        _authentications.add(authentication)
    }

}

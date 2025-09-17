package org.mansour.shared.domain

import jakarta.persistence.Embeddable
import org.mansour.shared.domain.exception.InvalidEmailException

@Embeddable
data class Email(
    val address: String
) {
    init {
        require(REGEX.matches(address)) { throw InvalidEmailException() }
    }

    companion object {
        private const val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        private val REGEX = EMAIL_REGEX.toRegex()
    }
}

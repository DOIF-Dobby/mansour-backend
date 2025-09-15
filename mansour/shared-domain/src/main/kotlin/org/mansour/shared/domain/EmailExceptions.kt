package org.mansour.shared.domain

import org.mj.mansour.system.core.exception.BaseException

class InvalidEmailException : BaseException(code = "INVALID_EMAIL", message = "Invalid email format", messageProperty = "domain.email.invalid")

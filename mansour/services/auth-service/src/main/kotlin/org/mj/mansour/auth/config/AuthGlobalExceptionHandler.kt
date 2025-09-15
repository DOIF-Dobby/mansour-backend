package org.mj.mansour.auth.config

import org.mj.mansour.system.api.exception.AbstractGlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthGlobalExceptionHandler : AbstractGlobalExceptionHandler() {
}

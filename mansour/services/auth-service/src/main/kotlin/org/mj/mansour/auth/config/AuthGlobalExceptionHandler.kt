package org.mj.mansour.auth.config

import org.mj.mansour.system.webmvc.advice.AbstractGlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthGlobalExceptionHandler : AbstractGlobalExceptionHandler()

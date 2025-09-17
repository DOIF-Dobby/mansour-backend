package org.mj.mansour.user.config

import org.mj.mansour.system.webmvc.advice.AbstractGlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserGlobalExceptionHandler : AbstractGlobalExceptionHandler()

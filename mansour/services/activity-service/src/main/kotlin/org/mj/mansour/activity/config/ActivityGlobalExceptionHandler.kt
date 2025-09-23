package org.mj.mansour.activity.config

import org.mj.mansour.system.webmvc.advice.AbstractGlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ActivityGlobalExceptionHandler : AbstractGlobalExceptionHandler()

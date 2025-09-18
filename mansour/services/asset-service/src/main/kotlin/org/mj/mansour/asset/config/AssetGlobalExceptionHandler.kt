package org.mj.mansour.asset.config

import org.mj.mansour.system.webmvc.advice.AbstractGlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AssetGlobalExceptionHandler : AbstractGlobalExceptionHandler()

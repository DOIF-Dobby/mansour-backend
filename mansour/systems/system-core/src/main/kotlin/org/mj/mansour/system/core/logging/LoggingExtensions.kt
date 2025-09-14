package org.mj.mansour.system.core.logging

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

inline val <reified T> T.log: KLogger
    get() = KotlinLogging.logger(T::class.java.name)

package org.mj.mansour.system.core.exception

import org.mj.mansour.system.core.message.getBundleMessage

abstract class BaseException(
    val code: String,
    override val message: String,
    val messageProperty: String = "base.fail",
    val messageArguments: Array<Any>? = null,
) : RuntimeException() {

    fun getLocalizedMessage(): String {
        return getBundleMessage(messageProperty, messageArguments, message)
    }
}

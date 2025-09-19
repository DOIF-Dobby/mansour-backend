package org.mj.mansour.system.web.response

import org.mj.mansour.system.core.message.getBundleMessage

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null
) {

    companion object {
        fun ok(message: String = getBundleMessage("web.ok")): ApiResponse<Unit> {
            return ApiResponse(
                code = "OK",
                message = message,
                data = Unit
            )
        }

        fun <T> ok(message: String = getBundleMessage("web.ok"), data: T? = null): ApiResponse<T> {
            return ApiResponse(
                code = "OK",
                message = message,
                data = data
            )
        }

        fun <T> fail(message: String = getBundleMessage("base.fail"), data: T? = null): ApiResponse<T> {
            return ApiResponse(
                code = "FAIL",
                message = message,
                data = data
            )
        }
    }
}

typealias UnitApiResponse = ApiResponse<Unit>

fun ApiResponse<*>.isSuccess(): Boolean {
    return this.code == "OK"
}

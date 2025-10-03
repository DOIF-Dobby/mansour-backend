package org.mj.mansour.system.web.response

typealias UnitApiResponse = ApiResponse<Unit>
typealias ContentApiResponse<T> = ApiResponse<Content<T>>

fun ApiResponse<*>.isSuccess(): Boolean {
    return this.code == "OK"
}

fun <T> ApiResponse.Companion.ok(content: List<T>): ContentApiResponse<T> {
    return ApiResponse.ok(data = Content.of(content))
}

package org.mj.mansour.system.feign

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import org.mj.mansour.system.web.response.ApiResponse

class FeignClientExecutor(
    private val objectMapper: ObjectMapper
) {
    
    fun <T> run(block: () -> ApiResponse<T>): ApiResponse<T> {
        return try {
            block()
        } catch (e: FeignException) {
            e.responseBody().map { byteBuffer ->
                try {
                    objectMapper.readValue(byteBuffer.array(), object : TypeReference<ApiResponse<T>>() {})
                } catch (_: Exception) {
                    makeUnknownErrorResponse()
                }
            }.orElse(null)
        } catch (_: Exception) {
            makeUnknownErrorResponse()
        }
    }

    private fun <T> makeUnknownErrorResponse(): ApiResponse<T> {
        return ApiResponse(
            code = "UNKNOWN_ERROR",
            message = "An unknown error occurred."
        )
    }
}

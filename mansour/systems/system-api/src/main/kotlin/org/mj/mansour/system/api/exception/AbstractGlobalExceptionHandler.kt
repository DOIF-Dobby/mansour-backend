package org.mj.mansour.system.api.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.mj.mansour.system.api.response.ApiResponse
import org.mj.mansour.system.api.response.UnitApiResponse
import org.mj.mansour.system.core.exception.BaseException
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.core.message.getBundleMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

abstract class AbstractGlobalExceptionHandler {

    /**
     * Exception 처리
     */
    @ExceptionHandler(Exception::class)
    open fun handleException(e: Exception): ResponseEntity<UnitApiResponse> {
        log.error { "${e::class.simpleName}: ${e.message}" }

        return ResponseEntity.internalServerError()
            .body(ApiResponse.fail())
    }

    /**
     * ApiException 처리
     */
    @ExceptionHandler(ApiException::class)
    open fun handleApiException(e: ApiException): ResponseEntity<UnitApiResponse> {
        log.error { "${e::class.simpleName}: ${e.message} | localizedMessage: ${e.localizedMessage} | code: ${e.code} | httpStatus: ${e.httpStatus}" }

        return ResponseEntity.status(e.httpStatus)
            .body(
                ApiResponse(
                    code = e.code,
                    message = e.localizedMessage
                )
            )
    }

    /**
     * BaseException 처리
     */
    @ExceptionHandler(BaseException::class)
    open fun handleBaseException(e: BaseException): ResponseEntity<UnitApiResponse> {
        log.error { "${e::class.simpleName}: ${e.message} | localizedMessage: ${e.localizedMessage} | code: ${e.code}" }

        return ResponseEntity.badRequest()
            .body(
                ApiResponse(
                    code = e.code,
                    message = e.localizedMessage
                )
            )
    }

    /**
     * BindException 처리 (Validation 검증 실패 시 발생)
     */
    @ExceptionHandler(BindException::class)
    open fun handleBindException(e: BindException): ResponseEntity<ApiResponse<Map<String, String>>> {
        log.error { "${e::class.simpleName}: ${e.message}" }

        val invalidFieldMap = mutableMapOf<String, String>()
        val bindingResult = e.bindingResult

        bindingResult.fieldErrors.forEach {
            invalidFieldMap[it.field] = it.defaultMessage ?: ""
        }


        return ResponseEntity.badRequest()
            .body(
                ApiResponse.fail(
                    data = invalidFieldMap,
                    message = getBundleMessage("api.error.invalid-request-data")
                )
            )
    }

    /**
     * API 요청 시, requestBody Object의 형식이 이상할 때 처리
     * (특히 JSON 콤마 문제나 숫자 타입인데 문자열로 요청한 경우 등등)
     *
     * 그리고 Kotlin non-null 타입의 경우, null로 요청한 경우에도 이 핸들러에서 처리됨
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    open fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Map<String, String>>> {
        log.error { "${e::class.simpleName}: ${e.message}" }

        val invalidFieldMap = mutableMapOf<String, String>()

        when (e.cause) {
            // non-null 타입에 null이 들어온 경우 / 숫자 타입에 문자열이 들어온 경우 등 처리
            is MismatchedInputException -> {
                val cause = e.cause as MismatchedInputException

                val reference = cause.path[0]
                val fromClass = reference.from as Class<*> // 역직렬화 대상 클래스
                val fieldName = reference.fieldName // 요청에서 문제가 있는 필드명
                val field = fromClass.getDeclaredField(fieldName) // 문제 있는 필드
                val type = field.type // 문제 있는 필드의 타입

                invalidFieldMap[fieldName] = if (field.type.isEnum) {
                    "Invalid enum value. Please use one of [${type.enumConstants.joinToString()}]"
                } else {
                    "Invalid value."
                }
            }
        }

        return ResponseEntity.badRequest()
            .body(
                ApiResponse.fail(
                    data = invalidFieldMap,
                    message = getBundleMessage("api.error.invalid-request-data")
                )
            )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    open fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<UnitApiResponse> {
        log.error { "${e::class.simpleName}: ${e.message}" }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ApiResponse.fail(
                    message = getBundleMessage("api.error.not-found")
                )
            )
    }
}

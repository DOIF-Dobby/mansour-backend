package org.mj.mansour.system.webmvc.util

import org.mj.mansour.system.web.header.ApiHeaders
import org.mj.mansour.system.webmvc.exception.UserHeaderNotFoundException
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class ApiHeaderUtils {

    companion object {
        /**
         * 현재 HTTP 요청의 "X-User-Id" 헤더에서 사용자 ID를 안전하게 추출합니다.
         *
         * @return Long 타입의 사용자 ID
         * @throws UserHeaderNotFoundException 헤더가 없거나, 숫자 형식이 아닐 경우
         */
        fun getCurrentUserId(): Long {
            val request = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
                ?: throw UserHeaderNotFoundException()

            val userIdHeader = request.getHeader(ApiHeaders.X_USER_ID)
                ?: throw UserHeaderNotFoundException()

            return userIdHeader.toLongOrNull()
                ?: throw UserHeaderNotFoundException()
        }
    }
}

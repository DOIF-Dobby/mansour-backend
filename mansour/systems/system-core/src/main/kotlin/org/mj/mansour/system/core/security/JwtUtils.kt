package org.mj.mansour.system.core.security

import java.util.Base64

class JwtUtils {
    companion object {
        fun decodePayload(token: String): String {
            val parts = token.split(".")
            return String(Base64.getUrlDecoder().decode(parts[1]))
        }
    }
}

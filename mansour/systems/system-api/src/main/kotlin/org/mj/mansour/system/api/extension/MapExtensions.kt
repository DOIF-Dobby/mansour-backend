package org.mj.mansour.system.api.extension

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Converts a map of query parameters to a URL-encoded query string.
 *
 * @return A URL-encoded query string representation of the map.
 */
fun Map<String, String>.toQueryParams(): String {
    val charset = StandardCharsets.UTF_8.name()

    return this.entries.joinToString("&") { (key, value) ->
        val encodedKey = URLEncoder.encode(key, charset)
        val encodedValue = URLEncoder.encode(value, charset)
        "$encodedKey=$encodedValue"
    }

}

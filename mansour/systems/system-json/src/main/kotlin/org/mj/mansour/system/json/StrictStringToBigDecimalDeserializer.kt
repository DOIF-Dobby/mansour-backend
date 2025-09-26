package org.mj.mansour.system.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.math.BigDecimal

class StrictStringToBigDecimalDeserializer : JsonDeserializer<BigDecimal>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): BigDecimal {
        val text = p.text
        if (text.isNullOrBlank()) {
            throw IOException("Attempted to deserialize a blank string to a non-nullable BigDecimal.")
        }
        try {
            return text.toBigDecimal()
        } catch (e: NumberFormatException) {
            // 숫자 형식이 아니면 예외 발생
            throw IOException("Invalid number format for BigDecimal: $text", e)
        }
    }
}

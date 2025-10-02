package org.mj.mansour.system.data.jpa.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonConverter(
    private val objectMapper: ObjectMapper
) : AttributeConverter<Map<String, Any>, String> {

    override fun convertToDatabaseColumn(attribute: Map<String, Any>?): String? {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any>? {
        return if (dbData.isNullOrEmpty()) {
            emptyMap()
        } else {
            objectMapper.readValue(dbData, object : TypeReference<Map<String, Any>>() {})
        }
    }
}

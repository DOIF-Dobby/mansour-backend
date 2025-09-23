package org.mj.mansour.system.kafka

import com.fasterxml.jackson.databind.ObjectMapper

class DebeziumMessageParser(
    private val objectMapper: ObjectMapper,
) {

    fun <T> parseDebeziumPayload(rawMessage: String, clazz: Class<T>): T {
        val innerJsonString = objectMapper.readValue(rawMessage, String::class.java)
        return objectMapper.readValue(innerJsonString, clazz)
    }
}


inline fun <reified T> DebeziumMessageParser.parsePayload(rawMessage: String): T {
    return this.parseDebeziumPayload(rawMessage, T::class.java)
}

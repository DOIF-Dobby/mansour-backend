package org.mj.mansour.infra.gateway.filter

import org.mj.mansour.infra.gateway.jwt.JwtTokenValidator
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationGatewayFilterFactory(
    private val jwtTokenValidator: JwtTokenValidator
) : AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config>(Config::class.java) {

    class Config

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return@GatewayFilter onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED)
            }

            val token = authHeader.substring(7)


            try {
                val subject = jwtTokenValidator.getSubject(token)
                val mutatedRequest = request.mutate()
                    .header("X-User-Id", subject)
                    .build()

                chain.filter(exchange.mutate().request(mutatedRequest).build())
            } catch (e: Exception) {
                onError(exchange, "Invalid token: ${e.message}", HttpStatus.UNAUTHORIZED)
            }

        }
    }

    private fun onError(exchange: ServerWebExchange, errorMessage: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus

        val dataBuffer = response.bufferFactory().wrap(errorMessage.toByteArray())
        return response.writeWith(Mono.just(dataBuffer))
    }
}

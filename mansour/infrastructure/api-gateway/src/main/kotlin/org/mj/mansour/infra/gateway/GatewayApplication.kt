package org.mj.mansour.infra.gateway

import org.mj.mansour.infra.gateway.jwt.JwtTokenProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableConfigurationProperties(JwtTokenProperties::class)
@EnableDiscoveryClient
@SpringBootApplication
class GatewayApplication

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}


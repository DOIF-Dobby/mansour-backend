package org.mj.mansour.activity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableFeignClients
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class ActivityApplication

fun main(args: Array<String>) {
    runApplication<ActivityApplication>(*args)
}

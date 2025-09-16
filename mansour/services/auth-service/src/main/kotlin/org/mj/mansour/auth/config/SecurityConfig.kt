package org.mj.mansour.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // 테스트 및 stateless API에서는 보통 비활성화
            .authorizeHttpRequests { auth ->
                auth
                    .anyRequest().permitAll()
            }


        return http.build()
    }
}

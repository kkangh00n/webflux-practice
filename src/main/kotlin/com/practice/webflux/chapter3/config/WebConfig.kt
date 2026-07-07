package com.practice.webflux.chapter3.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebConfig {

    @Bean
    fun getWebClient(): WebClient {
        return WebClient.builder().build()
    }

}
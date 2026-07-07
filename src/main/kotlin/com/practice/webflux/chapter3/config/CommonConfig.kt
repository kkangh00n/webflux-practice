package com.practice.webflux.chapter3.config

import com.practice.webflux.chapter3.client.LlmType
import com.practice.webflux.chapter3.client.LlmWebClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig {

    @Bean
    fun getLlmWebClientServiceMap(
        llmWebClientList: List<LlmWebClient>
    ): Map<LlmType, LlmWebClient> {
        return llmWebClientList.associateBy(LlmWebClient::getLlmType)
    }

}
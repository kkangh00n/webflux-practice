package com.practice.webflux.chapter3.client

import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto
import reactor.core.publisher.Mono

interface LlmWebClient {

    fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto>

    fun getLlmType(): LlmType
}
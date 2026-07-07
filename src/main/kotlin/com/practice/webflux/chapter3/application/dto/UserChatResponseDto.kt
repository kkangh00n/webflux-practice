package com.practice.webflux.chapter3.application.dto

import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto

data class UserChatResponseDto(
    var response: String
) {
    constructor(
        llmChatResponseDto: LlmChatResponseDto
    ) : this(response = llmChatResponseDto.llmResponse)
}

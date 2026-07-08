package com.practice.webflux.chapter3.application.dto

import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto
import com.practice.webflux.chapter3.exception.CommonError

data class UserChatResponseDto(
    var title: String? = null,
    var response: String? = null,
    var error: CommonError? = null
) {
    constructor(
        llmChatResponseDto: LlmChatResponseDto
    ) : this(response = llmChatResponseDto.llmResponse)
}

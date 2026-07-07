package com.practice.webflux.chapter3.application.dto

import com.practice.webflux.chapter3.client.LlmModel

data class UserChatRequestDto(
    val request: String,
    val llmModel: LlmModel
)

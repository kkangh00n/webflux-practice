package com.practice.webflux.chapter3.client.dto

import com.practice.webflux.chapter3.client.gemini.dto.GeminiChatResponseDto
import java.io.Serializable

data class LlmChatResponseDto(
    var llmResponse: String
) : Serializable {

    constructor(
        geminiChatResponseDto: GeminiChatResponseDto
    ) : this(
        llmResponse = geminiChatResponseDto.getSingleText()
    )
}

package com.practice.webflux.chapter3.client.dto

import com.practice.webflux.chapter3.client.gemini.dto.GeminiChatResponseDto
import com.practice.webflux.chapter3.exception.CommonError
import java.io.Serializable

data class LlmChatResponseDto(
    var llmResponse: String? = null,
    var error: CommonError? = null

) : Serializable {

    constructor(
        geminiChatResponseDto: GeminiChatResponseDto
    ) : this(
        llmResponse = geminiChatResponseDto.getSingleText()
    )

    constructor(
        error: CommonError
    ) : this(
        llmResponse = null,
        error = error
    )
}

package com.practice.webflux.chapter3.client.dto

import com.practice.webflux.chapter3.client.gemini.dto.GeminiChatResponseDto
import com.practice.webflux.chapter3.exception.CommonError
import java.util.*

data class LlmChatResponseDto(
    var title: String? = null,
    var llmResponse: String? = null,
    var error: CommonError? = null
) {

    fun isValid(): Boolean {
        return error == null
    }

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

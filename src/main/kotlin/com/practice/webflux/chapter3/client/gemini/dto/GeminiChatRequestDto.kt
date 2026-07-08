package com.practice.webflux.chapter3.client.gemini.dto

import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.client.gemini.GeminiMessageRole

data class GeminiChatRequestDto(
    val contents: List<GeminiContent>,
    val systemInstruction: GeminiContent,
    val generationConfig: GeminiGenerationConfigDto? = null
) {

    constructor(
        llmChatRequestDto: LlmChatRequestDto
    ) : this(
        contents = listOf(
            GeminiContent(listOf(GeminiPart(llmChatRequestDto.userRequest)), GeminiMessageRole.USER)
        ),
        systemInstruction = GeminiContent(listOf(GeminiPart(llmChatRequestDto.systemPrompt))),
        generationConfig = if (llmChatRequestDto.useJson != null || llmChatRequestDto.useJson == true) GeminiGenerationConfigDto() else null
    )
}
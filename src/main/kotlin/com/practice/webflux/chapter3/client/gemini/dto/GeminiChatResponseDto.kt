package com.practice.webflux.chapter3.client.gemini.dto

data class GeminiChatResponseDto(
    var candidates: List<GeminiCandidate>
) {
    fun getSingleText(): String {
        return candidates.firstOrNull()
            ?.content?.parts?.firstOrNull()
            ?.text
            ?: throw RuntimeException()
    }
}

package com.practice.webflux.chapter3.client.gemini.dto

data class GeminiCandidate(
    val content: GeminiContent,
    val finishReason: String? = null
)

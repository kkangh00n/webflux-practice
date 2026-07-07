package com.practice.webflux.chapter3.client.gemini.dto

import com.practice.webflux.chapter3.client.gemini.GeminiMessageRole

data class GeminiContent(
    var parts: List<GeminiPart>,
    var role: GeminiMessageRole? = null
)

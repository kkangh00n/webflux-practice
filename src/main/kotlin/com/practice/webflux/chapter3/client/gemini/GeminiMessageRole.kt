package com.practice.webflux.chapter3.client.gemini

enum class GeminiMessageRole {
    USER,
    MODEL;

    override fun toString(): String {
        return name.lowercase()
    }
}
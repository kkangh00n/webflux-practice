package com.practice.webflux.chapter3.client

enum class LlmModel(
    val code: String,
    val llmType: LlmType
) {

    GPT_40("gpt-4o", LlmType.GPT),
    GEMINI_2_5_FLASH("gemini-2.5-flash", LlmType.GEMINI)
    ;

    override fun toString(): String {
        return code
    }

}
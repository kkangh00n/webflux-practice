package com.practice.webflux.chapter3.exception

enum class CustomErrorType(
    val code: Int
) {

    GEMINI_RESPONSE_ERROR(1),
    GPT_RESPONSE_ERROR(2),
    ;

}
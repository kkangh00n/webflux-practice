package com.practice.webflux.chapter3.exception

class ErrorTypeException(
    val errorType: CustomErrorType,
    message: String? = null
) : RuntimeException(message) {

    override val message: String
        get() = "Code: ${errorType.code} +  Message : ${super.message}"
}
package com.practice.webflux.chapter3.exception

class ErrorTypeException(
    val errorType: CustomErrorType
) : RuntimeException() {

    override val message: String
        get() = "Code: ${errorType.code} +  Message : ${super.message}"
}
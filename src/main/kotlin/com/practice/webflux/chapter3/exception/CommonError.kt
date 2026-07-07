package com.practice.webflux.chapter3.exception

class CommonError(
    val errorCode: String,
    val errorMessage: String?
) {

    constructor(
        errorCode: Int,
        errorMessage: String?
    ) : this(
        errorCode = errorCode.toString(),
        errorMessage = errorMessage
    )


}
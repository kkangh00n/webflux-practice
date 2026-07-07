package com.practice.webflux.chapter3.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(java.lang.Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handlerGeneralException(
        ex: Exception,
        exchange: ServerWebExchange
    ): Mono<ErrorResponse> {
        val request = exchange.request
        //로깅 처리
        val commonError = CommonError("500", ex.message)
        return Mono.just(ErrorResponse(commonError))
    }

    @ExceptionHandler(ErrorTypeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleErrorTypeException(
        ex: ErrorTypeException,
        exchange: ServerWebExchange
    ): Mono<ErrorResponse> {
        val request = exchange.request
        val commonError = CommonError(ex.errorType.code, ex.message)
        return Mono.just(ErrorResponse(commonError))
    }



}
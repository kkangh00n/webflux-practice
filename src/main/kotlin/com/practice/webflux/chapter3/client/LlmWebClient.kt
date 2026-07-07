package com.practice.webflux.chapter3.client

import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto
import com.practice.webflux.chapter3.exception.CommonError
import com.practice.webflux.chapter3.exception.ErrorTypeException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function

interface LlmWebClient {

    fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto>

    /**
     * 스트림 내 발생한 예외로 인해, 스트림을 중단하고 싶지 않을 경우 사용!
     */
    fun getChatCompletionWithCatchException(requestDto: LlmChatRequestDto?): Mono<LlmChatResponseDto> {
        return getChatCompletion(requestDto!!)
            .onErrorResume(Function { exception: Throwable? ->
                if (exception is ErrorTypeException) {
                    val commonError =
                        CommonError(exception.errorType.code, exception.message)
                    Mono.just(LlmChatResponseDto(commonError))
                } else {
                    val commonError = CommonError(500, exception!!.message)
                    Mono.just(LlmChatResponseDto(commonError))
                }
            })
    }

    fun getLlmType(): LlmType

    fun getChatCompletionStream(requestDto: LlmChatRequestDto): Flux<LlmChatResponseDto>
}
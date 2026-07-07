package com.practice.webflux.chapter3.client.gemini

import com.practice.webflux.chapter3.client.LlmType
import com.practice.webflux.chapter3.client.LlmWebClient
import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto
import com.practice.webflux.chapter3.client.gemini.dto.GeminiChatRequestDto
import com.practice.webflux.chapter3.client.gemini.dto.GeminiChatResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GeminiWebClient(
    private val webClient: WebClient,
    @Value("\${llm.gemini.key}") val geminiApiKey: String
) : LlmWebClient {

    override fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto> {
        val geminiChatRequestDto = GeminiChatRequestDto(requestDto)
        return webClient.post()
            .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$geminiApiKey")
            .bodyValue(geminiChatRequestDto)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .flatMap { body -> Mono.error(RuntimeException("API 요청 실패: $body")) }
            }
            .bodyToMono(GeminiChatResponseDto::class.java)
            .map { LlmChatResponseDto(it) }
    }

    override fun getLlmType(): LlmType {
        return LlmType.GEMINI
    }

    override fun getChatCompletionStream(requestDto: LlmChatRequestDto): Flux<LlmChatResponseDto> {
        val geminiChatRequestDto = GeminiChatRequestDto(requestDto)
        return webClient.post()
            .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:streamGenerateContent?alt=sse&key=$geminiApiKey")
            .bodyValue(geminiChatRequestDto)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .flatMap { body -> Mono.error(RuntimeException("API 요청 실패: $body")) }
            }
            .bodyToFlux(GeminiChatResponseDto::class.java)
            .map { LlmChatResponseDto(it) }
    }

}
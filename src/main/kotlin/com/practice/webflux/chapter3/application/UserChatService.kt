package com.practice.webflux.chapter3.application

import com.practice.webflux.chapter3.client.LlmType
import com.practice.webflux.chapter3.client.LlmWebClient
import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.client.dto.LlmChatResponseDto
import com.practice.webflux.chapter3.application.dto.UserChatRequestDto
import com.practice.webflux.chapter3.application.dto.UserChatResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@RequiredArgsConstructor
class UserChatService(
    private val llmWebClientServiceMap: Map<LlmType, LlmWebClient>
) {

    fun getOneShotChat(userChatRequestDto: UserChatRequestDto): Mono<UserChatResponseDto> {
        val llmChatRequestDto = LlmChatRequestDto(userChatRequestDto, "요청에 적절히 응답해주세요.")
        val chatCompletionMono: Mono<LlmChatResponseDto> = llmWebClientServiceMap.getValue(llmChatRequestDto.llmModel.llmType)
            .getChatCompletion(llmChatRequestDto)
        return chatCompletionMono.map{ UserChatResponseDto(it) }
    }
}
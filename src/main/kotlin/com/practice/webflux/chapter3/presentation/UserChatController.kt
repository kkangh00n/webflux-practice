package com.practice.webflux.chapter3.presentation

import com.practice.webflux.chapter3.application.ChainOfThoughtService
import com.practice.webflux.chapter3.application.UserChatService
import com.practice.webflux.chapter3.application.dto.UserChatRequestDto
import com.practice.webflux.chapter3.application.dto.UserChatResponseDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/chat")
class UserChatController(
    private val userChatService: UserChatService,
    private val chainOfThoughtService: ChainOfThoughtService
) {

    // application/json 형태 응답
    @PostMapping("/oneshot")
    fun oneShotChat(@RequestBody userChatRequestDto: UserChatRequestDto): Mono<UserChatResponseDto> {
        return userChatService.getOneShotChat(userChatRequestDto)
    }

    // text/event-stream 형태 응답
    // Server-Sent Events (SSE) 프로토콜을 사용
    //1. 클라이언트에서 Syn 전송 -> 3way handshake 연결 시작
    //2. 서버에서 데이터 청크 전송 -> data:"내용"\n\n
    //3. 서버에서 Fin 전송 -> 4way handshake 연결 종료
    @PostMapping("/oneshot/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun oneShotChatStream(@RequestBody userChatRequestDto: UserChatRequestDto): Flux<UserChatResponseDto> {
        return userChatService.getOneShotChatStream(userChatRequestDto)
    }

    @PostMapping("/cot", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun chainOfThought(@RequestBody userChatRequestDto: UserChatRequestDto): Flux<UserChatResponseDto> {
        //서비스에서 request가공해서 response돌려줘야함.
        return chainOfThoughtService.getChainOfThoughtResponse(userChatRequestDto)
    }
}
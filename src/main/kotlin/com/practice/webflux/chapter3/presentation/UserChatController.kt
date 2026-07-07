package com.practice.webflux.chapter3.presentation

import com.practice.webflux.chapter3.application.UserChatService
import com.practice.webflux.chapter3.application.dto.UserChatRequestDto
import com.practice.webflux.chapter3.application.dto.UserChatResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
class UserChatController(
    private val userChatService: UserChatService
) {

    @PostMapping("/oneshot")
    fun oneShotChat(@RequestBody userChatRequestDto: UserChatRequestDto): Mono<UserChatResponseDto> {
        return userChatService.getOneShotChat(userChatRequestDto)
    }
}
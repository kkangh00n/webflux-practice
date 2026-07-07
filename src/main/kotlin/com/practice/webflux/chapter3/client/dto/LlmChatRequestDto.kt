package com.practice.webflux.chapter3.client.dto

import com.practice.webflux.chapter3.client.LlmModel
import com.practice.webflux.chapter3.application.dto.UserChatRequestDto

data class LlmChatRequestDto(
    var userRequest: String,
    var systemPrompt: String,
    var useJson: Boolean = true,
    var llmModel: LlmModel
) {
    constructor(
        userChatRequestDto: UserChatRequestDto,
        systemPrompt: String
    ) : this(
        userRequest = userChatRequestDto.request,
        systemPrompt = systemPrompt,
        llmModel = userChatRequestDto.llmModel
    )
}

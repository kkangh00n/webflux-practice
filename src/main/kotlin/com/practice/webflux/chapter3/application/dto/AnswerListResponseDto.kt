package com.practice.webflux.chapter3.application.dto

data class AnswerListResponseDto(
    val answerList: List<String>
) {
    override fun toString(): String {
        return answerList.joinToString("\n\n")
    }
}

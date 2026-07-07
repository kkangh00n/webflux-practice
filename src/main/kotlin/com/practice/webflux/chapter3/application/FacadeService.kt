package com.practice.webflux.chapter3.application

import com.practice.webflux.chapter3.application.dto.FacadeAvailableModel
import com.practice.webflux.chapter3.application.dto.FacadeHomeResponseDto
import com.practice.webflux.chapter3.client.LlmModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FacadeService() {

    fun getFacadeHomeResponseDto(): Mono<FacadeHomeResponseDto> {
        return Mono.fromCallable {
            val availableModelList = LlmModel.entries
                .map { FacadeAvailableModel(it.name, it.code) }
            FacadeHomeResponseDto(availableModelList)
        }
    }

}
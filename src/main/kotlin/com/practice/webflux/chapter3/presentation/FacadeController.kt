package com.practice.webflux.chapter3.presentation

import com.practice.webflux.chapter3.application.FacadeService
import com.practice.webflux.chapter3.application.dto.FacadeHomeResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/facade")
class FacadeController(
    private val facadeService: FacadeService
) {

    @PostMapping("/home")
    fun homeFacade(): Mono<FacadeHomeResponseDto> {
        return facadeService.getFacadeHomeResponseDto()
    }

}
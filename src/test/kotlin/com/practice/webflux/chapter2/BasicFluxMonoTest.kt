package com.practice.webflux.chapter2

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class BasicFluxMonoTest {

    @Test
    fun testBasicFluxMono() {
        /**
         * Flux -> 0개 이상의 데이터를 방출할 수 있는 객체 -> List
         */
        //just 데이터로부터 흐름 시작
        //map과 filter를 통해 데이터 가공
        //subscribe로 데이터 방출
        Flux.just(1, 2, 3, 4, 5)
            .map { data -> data * 2 }
            .filter { it % 4 == 0 }
            .subscribe { data -> println("Flux가 구독한 data = $data") }

        /**
         * Mono -> 0개부터 1개의 데이터만 방출할 수 있는 객체 -> Optional
         */
        Mono.just(2)
            .map { data -> data * 2 }
            .filter { it % 4 == 0 }
            .subscribe { data -> println("Mono가 구독한 data = $data") }
    }
}
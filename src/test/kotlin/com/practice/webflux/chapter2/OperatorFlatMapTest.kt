package com.practice.webflux.chapter2

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class OperatorFlatMapTest {

    @Test
    @DisplayName("flatMap & flatMapSequential")
    fun testWebClientFlatMap() {
        val flatMap = Flux.just(
            callWebClient("1단계 - 문제 이해하기", 1500),
            callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
            callWebClient("3단계 - 최종 응답", 500)
        )
            //Flux<Mono<String>> -> Flux<String>
            .flatMap { it }

        /**
         * flatMap -> 출력순서는 입력순서를 보장하지 않음!! 처리 순서가 빠른대로 방출
         */
        flatMap.subscribe { println("FlatMapped data = $it") }



        val flatMapSequential = Flux.just(
            callWebClient("1단계 - 문제 이해하기", 1500),
            callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
            callWebClient("3단계 - 최종 응답", 500)
        )
            //Flux<Mono<String>> -> Flux<String>
            .flatMapSequential { it }

        /**
         * flatMapSequential -> 선입선출
         */
        flatMapSequential.subscribe { println("FlatMap Sequential data = $it") }

        Thread.sleep(2000)
    }


    //webClient를 통한 비동기 API 호출 가정
    fun callWebClient(request: String, delay: Long): Mono<String> {
        return Mono.defer {
            try {
                Thread.sleep(delay)
                Mono.just("$request -> 딜레이: $delay")
            } catch (e: Exception) {
                Mono.empty()
            }
        }.subscribeOn(Schedulers.boundedElastic())

    }
}
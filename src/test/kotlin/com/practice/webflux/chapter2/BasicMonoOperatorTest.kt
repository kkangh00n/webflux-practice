package com.practice.webflux.chapter2

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class BasicMonoOperatorTest {

    @Test
    @DisplayName("데이터로 시작하는 경우")
    fun startMonoFromData() {
        //just
        Mono.just(1).subscribe { println("data = $it") }

        //empty
        Mono.empty<String>().subscribe { println("data = $it") }
    }

    @Test
    @DisplayName("함수로 시작하는 경우")
    fun startMonoFromFunction() {
        //fromCallable -> 동기적인 객체를 반환
        //기존의 동기적인 로직을 Mono 형식으로 변환할 수 있음
        val monoFromCallable = Mono.fromCallable { callRestTemplate("안녕!") }

        //defer -> Mono객체를 Mono 객체로 반환
        val monoFromDefer = Mono.defer { Mono.just(callRestTemplate("안녕!")) }
        println("data 방출 전 (defer)")
        monoFromDefer.subscribe { println("data (defer) = $it") }

        //defer와 just의 차이 -> 데이터 생성 시점이 언제냐의 차이
        val monoFromJust = Mono.just(callRestTemplate("안녕!"))
        println("data 방출 전 (just)")
        monoFromJust.subscribe { println("data (just)= $it") }
    }

    @Test
    @DisplayName("flatMapMany")
    fun monoToFlux() {
        //flatMapMany -> Mono에서 Flux 변환
        val one = Mono.just(1)
        val intFlux = one.flatMapMany { Flux.just(it, it + 1, it + 2) }
        intFlux.subscribe { println("data = $it") }
    }

    private fun callRestTemplate(request: String): String {
        val result = request + "callRestTemplate 응답"
        println("data 생성")
        return result
    }
}
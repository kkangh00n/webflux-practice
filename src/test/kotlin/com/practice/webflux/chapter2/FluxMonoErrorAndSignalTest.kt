package com.practice.webflux.chapter2

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FluxMonoErrorAndSignalTest {

    @Test
    @DisplayName("doOnNext, doOnComplete, doOnError -> 흐름을 변경하지 않는 유틸리티 연산자")
    fun testBasicSignal() {
        Flux.just(1, 2, 3, 4)
            //데이터가 방출될 때마다
            .doOnNext { publishData -> println("publishData = $publishData") }
            //스트림이 끝났을 때
            .doOnComplete { println("스트림이 끝났습니다") }
            //에러가 발생할 때마다
            .doOnError { println("ex 에러상황 발생! = $it") }
            .subscribe { println("data = $it") }
    }

    @Test
    @DisplayName("스트림 내에서 발생하는 예외")
    fun testFluxMonoError() {

        //리액티브 스트림에서는 스트림 안에서 발생한 예외는 흐름 내부에서 자동으로 포착
        //예외 발생 시, 예외 시그널 발생 후 스트림은 즉각 중단
        try {
            Flux.just(1, 2, 3, 4)
                .map { data ->
                    if (data == 3) {
//                        throw RuntimeException()
                        //.error()를 통해서 예외 처리도 가능
                        Mono.error<RuntimeException>{ RuntimeException() }
                    }
                    data * 2
                }
                //operator를 통한 스트림 내 예외 처리
                .onErrorMap { ex -> IllegalArgumentException() }
                .onErrorReturn(999)
                .onErrorComplete()
                .subscribe { data -> println("data = $data") }
        } catch (e: Exception) {
            println("에러 발생")
        }

    }
}
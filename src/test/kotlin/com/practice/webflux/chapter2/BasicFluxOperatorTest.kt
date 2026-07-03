package com.practice.webflux.chapter2

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.util.context.Context
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.atomics.AtomicInt

class BasicFluxOperatorTest {

    @Test
    @DisplayName("데이터로 시작하는 경우")
    fun testFluxFromData() {
        //just
        Flux.just(1, 2, 3, 4)
            .subscribe { println("data = $it") }

        //fromIterable
        val basicList = listOf(1, 2, 3, 4)
        Flux.fromIterable(basicList)
            .subscribe { println("data fromIterable = $it") }
    }

    @Test
    @DisplayName("함수로 시작하는 경우")
    fun testFluxFromFunction() {
        //defer
        Flux.defer { Flux.just(1, 2, 3, 4) }
            .subscribe { println("data = $it") }

        //create
        Flux.create { sink ->
            sink.next(1)
            sink.next(2)
            sink.next(3)
            sink.complete()
        }.subscribe { println("data from sink = $it") }
    }

    @Test
    @DisplayName("FluxSink & context")
    fun testSinkDetail() {
        //FluxSink -> Flux 흐름
        Flux.create { sink -> recursiveFunction(sink) }
            //context -> sink의 변수를 추가하여 관리
            .contextWrite(Context.of("counter", AtomicInteger(0)))
            .subscribe { println("data from recursive = $it") }
    }

    private fun recursiveFunction(sink: FluxSink<String>) {
        //sink의 변수 값을 조회 후 변환
        val counter = sink.contextView().get<AtomicInteger>("counter")

        if (counter.incrementAndGet() < 10) {
            sink.next("sink count $counter")
            recursiveFunction(sink)
        } else {
            sink.complete()
        }
    }

    @Test
    @DisplayName("collectList")
    fun testFluxCollectList() {
        val listMono = Flux.just(1, 2, 3, 4, 5)
            .map { it * 2 }
            .filter { it % 4 == 0 }
            //collectList -> Flux를 Mono<List> 형태로 변환
            .collectList()

        listMono.subscribe { println("collectList가 변환한 list data = $it") }
    }
}
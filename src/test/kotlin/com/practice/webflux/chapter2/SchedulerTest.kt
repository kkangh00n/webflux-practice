package com.practice.webflux.chapter2

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class SchedulerTest {

    @Test
    fun testBasicFluxMono() {

        /**
         * Scheduler 종류
         *
         * 1. boundedElastic
         * - 호출 시 필요한 만큼 스레드를 즉시 생성
         * - 일정 시간 미사용 시 스레드를 자동 제거
         * - 생성 가능한 최대 스레드 수 제한
         * - 블로킹 작업 처리에 사용
         *
         * 2.parallel
         * - 최초 호출 시, CPU 코어 수와 동일한 갯수의 스레드 생성
         * - 삭제되지 않고 유지
         * - 스레드 수 고정
         * - CPU bound 작업 처리에 사용
         */

        Mono.just(2)
            //1. 구독을 시작하는 시점 스레드 -> boundedElastic에서 별도의 스레드를 할당
            .map {
                println("map Thread Name = ${Thread.currentThread().name}")
                it * 2
            }
            .publishOn(Schedulers.parallel())
            //2. parallel로 스레드 변경 -> 아래 작업부터 parallel에서 별도의 스레드를 할당하여 스트림 진행
            .filter {
                println("filter Thread Name = ${Thread.currentThread().name}")
                it % 4 == 0
            }
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe{println("Mono가 구독한 data!= $it + ${Thread.currentThread().name}")}
    }
}
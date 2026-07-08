package com.practice.webflux.chapter3.util

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap

private val loggerCache = ConcurrentHashMap<String, KLogger>()

val Any.log: KLogger
    get() {
        val className = cleanupClassName(this)
        return loggerCache.computeIfAbsent(className) {
            KotlinLogging.logger(it)
        }
    }


private fun cleanupClassName(instance: Any): String {
    val className = instance.javaClass.name

    // Spring CGLIB, Hibernate, Mockito 등 다양한 프록시 패턴 처리
    return className
        .substringBefore("$$")  // Spring CGLIB
        .substringBefore("_jvst")  // Javassist
}

package com.practice.webflux.chapter3.util

fun extractJsonString(
    content: String
): String {
    val startIndex = content.indexOf('{')
    val entIndex = content.lastIndexOf('}')
    if (startIndex != -1 && entIndex != -1 && startIndex < entIndex) {
        return content.substring(startIndex, entIndex + 1)
    }
    return ""
}
package com.vjh0107.barcode.framework.utils

import java.util.logging.Level
import java.util.logging.Logger

/**
 * context 를 추적 후 로그에 같이 표기합니다.
 */
fun Logger.traceableLog(logLevel: Level, message: String, artifact: String = "com.vjh0107.barcode") {
    val where = Thread
        .currentThread()
        .stackTrace
        .map { it.className }
        .last { it.startsWith(artifact) }
        .split(".")
        .last()
        .split("$")
        .first()
    this.log(logLevel, "[$where] $message")
}

fun Logger.traceableInfo(message: String) {
    this.traceableLog(Level.INFO, message)
}

fun Logger.traceableSevere(message: String) {
    this.traceableLog(Level.SEVERE, message)
}

fun Logger.traceableConfig(message: String) {
    this.traceableLog(Level.CONFIG, message)
}
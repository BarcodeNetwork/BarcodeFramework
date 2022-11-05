package com.vjh0107.barcode.framework.test

import com.vjh0107.barcode.framework.utils.traceableInfo
import com.vjh0107.barcode.framework.utils.traceableLog
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class LoggerTest : AnnotationSpec() {
    @Test
    fun traceableLogTest() {
        println("traceable log 가 어디서 호출되었지?")
        Logger.getGlobal().traceableLog(Level.INFO, "<-- 요기서")
    }

    @Test
    fun coroutineLogTest() {
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                Logger.getGlobal().traceableInfo("infofnonf")
            }
        }
    }
}
package com.vjh0107.barcode.framework.coroutine

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.service.CoroutinePluginService
import com.vjh0107.barcode.framework.koin.injector.inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

/**
 * 마인크래프트 메인쓰레드 디스패처를 구합니다.
 */
fun Dispatchers.MinecraftMain(plugin: AbstractBarcodePlugin): CoroutineContext {
    val coroutineDispatcher: CoroutinePluginService by inject { parametersOf(plugin) }
    return coroutineDispatcher.getCoroutinePlugin(plugin).mainDispatcher
}

/**
 * 비동기 디스패처를 구합니다. 비동기 버킷 스케줄러 쓰레드가 사용됩니다.
 */
fun Dispatchers.MinecraftAsync(plugin: AbstractBarcodePlugin): CoroutineContext {
    val coroutineDispatcher: CoroutinePluginService by inject { parametersOf(plugin) }
    return coroutineDispatcher.getCoroutinePlugin(plugin).asyncDispatcher
}

/**
 * 플러그인 코루틴 스코프를 구합니다.
 */
fun CoroutineScope.getFromPlugin(plugin: AbstractBarcodePlugin): CoroutineScope {
    val coroutineDispatcher: CoroutinePluginService by inject { parametersOf(plugin) }
    return coroutineDispatcher.getCoroutinePlugin(plugin).scope
}
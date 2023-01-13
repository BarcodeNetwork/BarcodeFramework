package com.vjh0107.barcode.framework.coroutine.plugin

import com.vjh0107.barcode.framework.coroutine.DisposableCoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface CoroutinePlugin {

    /**
     * 마인크래프트 코루틴 스코프 입니다.
     */
    val scope: CoroutineScope

    val mainDispatcher: DisposableCoroutineDispatcher

    val asyncDispatcher: DisposableCoroutineDispatcher
}
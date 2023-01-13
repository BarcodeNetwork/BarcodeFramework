package com.vjh0107.barcode.framework.coroutine

import kotlinx.coroutines.CoroutineDispatcher

abstract class DisposableCoroutineDispatcher : CoroutineDispatcher() {
    abstract fun dispose()
}
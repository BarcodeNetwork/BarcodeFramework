package com.vjh0107.barcode.framework.coroutine.repository

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.plugin.CoroutinePlugin

interface CoroutinePluginRepository {
    /**
     * 플러그인 코루틴 세션을 구합니다.
     */
    fun getCoroutinePlugin(plugin: AbstractBarcodePlugin): CoroutinePlugin

    /**
     * 플러그인 코루틴 세션을 삭제합니다.
     */
    fun removeCoroutinePlugin(plugin: AbstractBarcodePlugin)
}
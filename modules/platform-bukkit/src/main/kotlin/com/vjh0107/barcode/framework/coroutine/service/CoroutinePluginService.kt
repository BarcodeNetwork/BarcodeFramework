package com.vjh0107.barcode.framework.coroutine.service

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.plugin.CoroutinePlugin

interface CoroutinePluginService {
    /**
     * 플러그인 코루틴 세션을 구해옵니다.
     */
    fun getCoroutinePlugin(plugin: AbstractBarcodePlugin): CoroutinePlugin

    /**
     * 플러그인 코루틴 세션을 마칩니다.
     */
    fun closeCoroutinePlugin(plugin: AbstractBarcodePlugin)
}
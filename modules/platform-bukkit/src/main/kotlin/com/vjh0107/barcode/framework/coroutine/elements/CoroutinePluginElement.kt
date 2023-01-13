package com.vjh0107.barcode.framework.coroutine.elements

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class CoroutinePluginElement(val plugin: AbstractBarcodePlugin) : AbstractCoroutineContextElement(CoroutinePluginElement) {
    companion object Key : CoroutineContext.Key<CoroutinePluginElement>

    override fun toString(): String {
        return "CoroutinePluginElement{pluginName=${plugin.name}}"
    }
}
package com.vjh0107.barcode.framework.coroutine.elements

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class BarcodePluginElement(val plugin: AbstractBarcodePlugin) : AbstractCoroutineContextElement(BarcodePluginElement) {
    companion object Key : CoroutineContext.Key<BarcodePluginElement>

    override fun toString(): String {
        return "AbstractBarcodePluginElement{pluginName=${plugin.name}}"
    }
}
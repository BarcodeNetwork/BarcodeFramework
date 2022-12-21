package com.vjh0107.barcode.framework.component.handler.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.handler.AbstractBukkitComponentHandler
import com.vjh0107.barcode.framework.component.BarcodePluginManager
import com.vjh0107.barcode.framework.component.handler.BarcodeComponentHandler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@BarcodeComponentHandler
@Factory(binds = [AbstractBukkitComponentHandler::class])
@Named("BarcodePluginManagerHandler")
class BarcodePluginManagerHandler<P: AbstractBarcodePlugin>(
    plugin: P
) : AbstractBukkitComponentHandler<P, BarcodePluginManager>(plugin) {

    override fun validate(clazz: KClass<BarcodePluginManager>): Boolean {
        return clazz.isSubclassOf(BarcodePluginManager::class)
    }

    override fun onPostEnable() {
        this.getComponents().forEach {
            it.load()
        }
    }

    override fun onDisable() {
        this.getComponents().run {
            forEach { it.close() }
        }
    }
}
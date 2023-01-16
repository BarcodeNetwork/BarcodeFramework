package com.vjh0107.barcode.framework.component.handler.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.component.handler.AbstractVelocityComponentHandler
import com.vjh0107.barcode.framework.component.handler.BarcodeComponentHandler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@BarcodeComponentHandler
@Factory(binds = [AbstractVelocityComponentHandler::class])
@Named("BarcodeListenerHandler")
class BarcodeListenerHandler<T : AbstractBarcodePlugin>(
    plugin: T
) : AbstractVelocityComponentHandler<T, BarcodeListener>(plugin) {
    override fun validate(clazz: KClass<BarcodeListener>): Boolean {
        return clazz.isSubclassOf(BarcodeListener::class)
    }

    override fun onPostEnable() {
        this.getComponents().forEach { listener ->
            plugin.registerListener(listener)
        }
    }

    override fun onDisable() {
        plugin.server.eventManager.unregisterListeners(plugin)
    }
}
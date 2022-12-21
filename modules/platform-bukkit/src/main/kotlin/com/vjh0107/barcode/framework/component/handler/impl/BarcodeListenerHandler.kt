package com.vjh0107.barcode.framework.component.handler.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.handler.AbstractBukkitComponentHandler
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.component.handler.BarcodeComponentHandler
import com.vjh0107.barcode.framework.component.handler.HandlerPriority
import org.bukkit.event.HandlerList
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@BarcodeComponentHandler(priority = HandlerPriority.HIGH)
@Factory(binds = [AbstractBukkitComponentHandler::class])
@Named("BarcodeListenerHandler")
class BarcodeListenerHandler<P: AbstractBarcodePlugin>(
    plugin: P
) : AbstractBukkitComponentHandler<P, BarcodeListener>(plugin) {

    override fun validate(clazz: KClass<BarcodeListener>): Boolean {
        return clazz.isSubclassOf(BarcodeListener::class)
    }

    override fun onPostEnable() {
        this.getComponents().forEach {
            plugin.registerListener(it)
        }
    }

    override fun onDisable() {
        HandlerList.unregisterAll(plugin)
        plugin.logger.info("모든 ${plugin.name} 의 Listener 를 unregister 합니다.")
    }
}
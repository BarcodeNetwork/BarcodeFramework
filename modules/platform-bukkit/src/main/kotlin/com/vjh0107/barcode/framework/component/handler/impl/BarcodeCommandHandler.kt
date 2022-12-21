package com.vjh0107.barcode.framework.component.handler.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeCommand
import com.vjh0107.barcode.framework.component.handler.AbstractBukkitComponentHandler
import com.vjh0107.barcode.framework.component.handler.BarcodeComponentHandler
import dev.jorel.commandapi.CommandAPI
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@BarcodeComponentHandler
@Factory(binds = [AbstractBukkitComponentHandler::class])
@Named("BarcodeCommandHandler")
class BarcodeCommandHandler<P: AbstractBarcodePlugin>(
    plugin: P
) : AbstractBukkitComponentHandler<P, BarcodeCommand>(plugin) {

    override fun validate(clazz: KClass<BarcodeCommand>): Boolean {
        return clazz.isSubclassOf(BarcodeCommand::class)
    }

    override fun onPostEnable() {
        this.getComponents().forEach {
            it.command.register()
        }
    }

    override fun onDisable() {
        this.getComponents().forEach {
            CommandAPI.unregister(it.command.name)
        }
    }
}
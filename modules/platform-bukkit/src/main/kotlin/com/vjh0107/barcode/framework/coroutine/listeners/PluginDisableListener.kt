package com.vjh0107.barcode.framework.coroutine.listeners

import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.coroutine.service.CoroutinePluginService
import com.vjh0107.barcode.framework.events.BarcodePluginDisableEvent
import org.bukkit.event.EventHandler

@BarcodeComponent
class PluginDisableListener(private val service: CoroutinePluginService) : BarcodeListener {
    @EventHandler
    fun onDisable(event: BarcodePluginDisableEvent) {
        service.closeCoroutinePlugin(event.plugin)
    }
}
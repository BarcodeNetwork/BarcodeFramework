package com.vjh0107.barcode.framework.debug

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

@BarcodeComponent
class VersionAnnouncerSimpleModule(private val plugin: AbstractBarcodePlugin) : BarcodeListener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (plugin.config.getBoolean("debug.version-announcer", false)) {
            event.player.sendMessage("[BarcodeFramework] version: ${plugin.description.version}")
        }
    }
}
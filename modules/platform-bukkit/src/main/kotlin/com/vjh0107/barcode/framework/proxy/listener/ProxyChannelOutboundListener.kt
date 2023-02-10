package com.vjh0107.barcode.framework.proxy.listener

import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.events.ProxyChannelOutboundEvent
import com.vjh0107.barcode.framework.proxy.api.event.impl.BarcodeRepositorySaveStartEvent
import com.vjh0107.barcode.framework.proxy.api.event.sameAs
import com.vjh0107.barcode.framework.proxy.events.BarcodeRepositorySaveEvent
import com.vjh0107.barcode.framework.serialization.deserialize
import org.bukkit.Server
import org.bukkit.event.EventHandler

@BarcodeComponent
class ProxyChannelOutboundListener(private val server: Server) : BarcodeListener {
    @EventHandler
    fun onBukkitInbound(event: ProxyChannelOutboundEvent) {
        if (event.data.sameAs<BarcodeRepositorySaveStartEvent>()) {
            val eventData = event.data.serializedData.deserialize<BarcodeRepositorySaveStartEvent>()
            val player = server.getPlayer(eventData.minecraftPlayerUUID) ?: throw NullPointerException("${eventData.minecraftPlayerUUID} 플레이어가 없습니다.")
            server.pluginManager.callEvent(BarcodeRepositorySaveEvent(player))
        }
    }
}
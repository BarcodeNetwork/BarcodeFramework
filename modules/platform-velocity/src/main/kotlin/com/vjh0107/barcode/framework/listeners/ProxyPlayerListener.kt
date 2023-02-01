package com.vjh0107.barcode.framework.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerPostConnectEvent
import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.netty.service.NettyServerService
import com.vjh0107.barcode.framework.proxy.api.event.ProxyEventData
import com.vjh0107.barcode.framework.proxy.api.event.impl.ServerConnectedEvent

@Suppress("UnstableApiUsage")
@BarcodeComponent
class ProxyPlayerListener(
    private val plugin: AbstractBarcodePlugin,
    private val service: NettyServerService
) : BarcodeListener {
    @Subscribe
    fun onProxyConnect(event: ServerPostConnectEvent) {
        if (event.player.currentServer.isEmpty) {
            return
        }
        val connectedServer = event.player.currentServer.get()
        val connectedServerName = connectedServer.serverInfo.name
        val serverConnectedEvent = ServerConnectedEvent(
            event.player.uniqueId,
            connectedServer.serverInfo.name,
            event.previousServer?.serverInfo?.name
        )
        val proxyEventData = ProxyEventData.serialize(serverConnectedEvent)
        if (!plugin.ignoringInfoMessages) {
            plugin.getLogger().info("data: $proxyEventData, send to $connectedServerName")
        }
        service.sendMessage(connectedServerName, proxyEventData, plugin.ignoringInfoMessages)
    }
}
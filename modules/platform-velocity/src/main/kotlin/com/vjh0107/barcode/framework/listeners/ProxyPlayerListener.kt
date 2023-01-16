package com.vjh0107.barcode.framework.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.netty.service.NettyServerService
import com.vjh0107.barcode.framework.proxy.api.ProxyEventData
import com.vjh0107.barcode.framework.proxy.api.events.ServerConnectedEventWrapper
import com.vjh0107.barcode.framework.serialization.serialize
import kotlin.jvm.optionals.getOrNull

@BarcodeComponent
class ProxyPlayerListener(
    private val plugin: AbstractBarcodePlugin,
    private val service: NettyServerService
) : BarcodeListener {
    @OptIn(ExperimentalStdlibApi::class)
    @Subscribe
    fun onProxyConnect(event: ServerConnectedEvent) {
        val connectedServerName = event.server.serverInfo.name
        val wrapper = ServerConnectedEventWrapper(
            event.player.uniqueId,
            event.server.serverInfo.name,
            event.previousServer.getOrNull()?.serverInfo?.name
        )
        val proxyEventData = ProxyEventData.serialize(
            ServerConnectedEventWrapper.id,
            wrapper.serialize(),
            event.server.serverInfo.address.port
        )
        service.sendMessage(connectedServerName, proxyEventData, plugin.ignoringInfoMessages)
    }
}
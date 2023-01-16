package com.vjh0107.barcode.framework.events

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import com.vjh0107.barcode.framework.proxy.api.ProxyEventData
import com.vjh0107.barcode.framework.utils.getServerByPort
import io.netty.channel.ChannelHandlerContext

class ProxyChannelInboundEvent(
    val server: ProxyServer,
    val context: ChannelHandlerContext,
    val proxyEventData: ProxyEventData
) {
    fun getRegisteredServer(): RegisteredServer {
        return server.getServerByPort(proxyEventData.port)
    }
}
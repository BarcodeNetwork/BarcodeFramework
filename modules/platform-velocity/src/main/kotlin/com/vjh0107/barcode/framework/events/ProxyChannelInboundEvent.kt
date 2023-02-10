package com.vjh0107.barcode.framework.events

import com.velocitypowered.api.proxy.server.RegisteredServer
import com.vjh0107.barcode.framework.proxy.api.event.ProxyEventData
import io.netty.channel.ChannelHandlerContext

class ProxyChannelInboundEvent(
    val server: RegisteredServer,
    val context: ChannelHandlerContext,
    val eventData: ProxyEventData
)
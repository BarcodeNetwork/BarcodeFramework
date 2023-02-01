package com.vjh0107.barcode.framework.events

import com.velocitypowered.api.proxy.server.RegisteredServer
import io.netty.channel.ChannelHandlerContext

class ProxyChannelInboundEvent(
    val server: RegisteredServer,
    val context: ChannelHandlerContext,
    val message: String
)
package com.vjh0107.barcode.framework.netty.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ListenerBoundEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.netty.service.NettyServerService
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import org.koin.core.annotation.Named
import org.slf4j.Logger

@BarcodeComponent
class NettyServerInitializationListener(
    private val plugin: AbstractBarcodePlugin,
    private val logger: Logger,
    private val nettyServerService: NettyServerService,
    @Named("velocitychannelinboundadapter") private val initializer: ChannelInitializer<SocketChannel>
) : BarcodeListener {
    @Subscribe
    fun onProxyInitialization(event: ListenerBoundEvent) {
        val config = plugin.getConfig("config.yml").getNode("netty")
        logger.info("netty server service initializing...")
        val host = config.getNode("host").getString("localhost")
        val port = config.getNode("port").getInt(30107)
        nettyServerService.startServerBootStrap(host, port) {
            childHandler(initializer)
        }

        logger.info("netty server service started")
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        nettyServerService.close()
        logger.info("netty server service closed")
    }
}
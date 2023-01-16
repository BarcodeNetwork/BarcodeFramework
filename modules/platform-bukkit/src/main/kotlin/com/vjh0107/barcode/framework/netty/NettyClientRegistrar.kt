package com.vjh0107.barcode.framework.netty

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeRegistrable
import com.vjh0107.barcode.framework.component.handler.impl.registrable.Registrar
import com.vjh0107.barcode.framework.component.handler.impl.registrable.UnRegistrar
import com.vjh0107.barcode.framework.netty.service.NettyClientService
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Named
import kotlin.coroutines.CoroutineContext

@BarcodeComponent
class NettyClientRegistrar(
    private val plugin: AbstractBarcodePlugin,
    private val nettyClientService: NettyClientService,
    @Named("bukkitchannelinboundadapter") private val initializer: ChannelInitializer<SocketChannel>
) : BarcodeRegistrable, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO
    @Registrar
    fun registerNettyClient() {
        val config = plugin.config
        if (config.getBoolean("netty.enabled", false)) {
            val host = config.getString("netty.host", "localhost")!!
            val port = config.getInt("netty.port", 30107)
            launch {
                nettyClientService.startBootstrap(host, port) {
                    handler(initializer)
                }
            }
            plugin.logger.info("netty server service started")
        }
    }

    @UnRegistrar
    fun closeNettyClient() {
        val config = plugin.config
        if (config.getBoolean("netty.enabled", false)) {
            nettyClientService.close()
            plugin.logger.info("netty server service closed")
        }
    }
}
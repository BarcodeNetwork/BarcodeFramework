package com.vjh0107.barcode.framework.netty

import com.vjh0107.barcode.framework.events.ProxyChannelOutboundEvent
import com.vjh0107.barcode.framework.netty.service.NettyClientService
import com.vjh0107.barcode.framework.proxy.api.ProxyEventData
import com.vjh0107.barcode.framework.utils.uncheckedNonnullCast
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.Server
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext

/**
 * channel 이 active 될 때, proxy 서버에
 */
@Named("bukkitchannelinboundadapter")
@Factory(binds = [ChannelInitializer::class])
class BukkitChannelInboundAdapter(
    private val service: NettyClientService,
    private val server: Server,
    private val logger: Logger
) : ChannelInitializer<SocketChannel>(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    override fun initChannel(channel: SocketChannel) {
        with(ChannelPipelineDelegate(channel.pipeline())) {
            addChannelReadHandler { context, message ->
                Bukkit.getPluginManager().callEvent(ProxyChannelOutboundEvent(context, ProxyEventData.deserialize(message)))
            }
            addChannelUnregisteredHandler { context ->
                val address = context.channel().remoteAddress().uncheckedNonnullCast<InetSocketAddress>()
                val host = address.hostString
                val port = address.port
                launch {
                    delay(5000L)
                    logger.info("[BarcodeFramework] attempting to reconnect to proxy... host: $host, port: $port")
                    service.startBootstrap(host, port) {
                        handler(this@BukkitChannelInboundAdapter)
                    }
                }
                logger.info("[BarcodeFramework] connection lost from proxy")
            }
            addChannelInactiveHandler { context ->
                val connectRequest = NettyPredefinedMessages.INACTIVE.message + server.port
                val messageBuffer = Unpooled.buffer()
                messageBuffer.writeBytes(connectRequest.toByteArray())
                context.writeAndFlush(messageBuffer)
                logger.info("[BarcodeFramework] disconnected from proxy")
            }
            addChannelActiveHandler { context ->
                val connectRequest = NettyPredefinedMessages.ACTIVE.message + server.port
                val messageBuffer = Unpooled.buffer()
                messageBuffer.writeBytes(connectRequest.toByteArray())
                context.writeAndFlush(messageBuffer)
                service.setContext(context)
                logger.info("[BarcodeFramework] connected to proxy")
            }
            addExceptionCaughtHandler { context, cause ->
                cause.printStackTrace()
                context.close()
            }
        }
    }
}
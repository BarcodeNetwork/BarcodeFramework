package com.vjh0107.barcode.framework.netty

import com.velocitypowered.api.proxy.ProxyServer
import com.vjh0107.barcode.framework.events.ProxyChannelInboundEvent
import com.vjh0107.barcode.framework.netty.repository.VelocityNettyServerContextRepository
import com.vjh0107.barcode.framework.proxy.api.event.ProxyEventData
import com.vjh0107.barcode.framework.utils.getServerByPort
import com.vjh0107.barcode.framework.utils.toInetSocketAddress
import com.vjh0107.barcode.framework.utils.uncheckedNonnullCast
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import org.slf4j.Logger
import java.net.InetSocketAddress

@Named("velocitychannelinboundadapter")
@Factory(binds = [ChannelInitializer::class])
class VelocityChannelInboundAdapter(
    private val server: ProxyServer,
    private val logger: Logger,
    private val repository: VelocityNettyServerContextRepository
) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(socketChannel: SocketChannel) {
        with(ChannelPipelineDelegate(socketChannel.pipeline())) {
            addChannelReadHandler { context, message ->
                if (message.startsWith(NettyPredefinedMessages.ACTIVE.message)) {
                    val port = message.removePrefix(NettyPredefinedMessages.ACTIVE.message).toInt()
                    val registeredServer = server.getServerByPort(port)
                    repository.addContext(registeredServer.serverInfo, context)
                } else if (message.startsWith(NettyPredefinedMessages.INACTIVE.message)) {
                    val port = message.removePrefix(NettyPredefinedMessages.ACTIVE.message).toInt()
                    val registeredServer = server.getServerByPort(port)
                    repository.removeContext(registeredServer.serverInfo)
                } else {
                    val port = context.channel().remoteAddress().toInetSocketAddress().port
                    val serverPort = repository.getRegisteredServerPortByChannelHandlerPort(port)
                    val registeredServer = server.getServerByPort(serverPort)
                    val eventData = ProxyEventData.deserialize(message)
                    server.eventManager.fire(ProxyChannelInboundEvent(registeredServer, context, eventData))
                }
            }
            addChannelRegisteredHandler { context ->
                logger.info("channel from port ${getRemoteAddress(context).port} registered")
            }
            addChannelUnregisteredHandler { context ->
                logger.info("channel from port ${getRemoteAddress(context).port} unregistered")
            }
            addExceptionCaughtHandler { context, cause ->
                cause.printStackTrace()
                context.close()
            }
        }
    }
    private fun getRemoteAddress(context: ChannelHandlerContext): InetSocketAddress {
        return context.channel().remoteAddress().uncheckedNonnullCast()
    }
}
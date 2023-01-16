package com.vjh0107.barcode.framework.netty

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import com.vjh0107.barcode.framework.events.ProxyChannelInboundEvent
import com.vjh0107.barcode.framework.netty.repository.NettyServerContextRepository
import com.vjh0107.barcode.framework.proxy.api.ProxyEventData
import com.vjh0107.barcode.framework.utils.getServerByPort
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
    private val repository: NettyServerContextRepository<ServerInfo>
) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(socketChannel: SocketChannel) {
        with(ChannelPipelineDelegate(socketChannel.pipeline())) {
            addChannelReadHandler<String> { context, message ->
                if (message.startsWith(NettyPredefinedMessages.ACTIVE.message)) {
                    val port = message.removePrefix(NettyPredefinedMessages.ACTIVE.message).toInt()
                    val registeredServer = server.getServerByPort(port)
                    repository.addContext(registeredServer.serverInfo, context)
                } else if (message.startsWith(NettyPredefinedMessages.INACTIVE.message)) {
                    val port = message.removePrefix(NettyPredefinedMessages.ACTIVE.message).toInt()
                    val registeredServer = server.getServerByPort(port)
                    repository.removeContext(registeredServer.serverInfo)
                } else {
                    val proxyEventData = ProxyEventData.deserialize(message)
                    server.eventManager.fire(ProxyChannelInboundEvent(server, context, proxyEventData))
                }
            }
            addChannelRegisteredHandler { context ->
                logger.info("channel from port ${getRemoteAddress(context).port} registered")
            }
            addChannelUnregisteredHandler { context ->
                logger.info("channel from port ${getRemoteAddress(context).port} unregistered")
            }
        }
    }
    private fun getRemoteAddress(context: ChannelHandlerContext): InetSocketAddress {
        return context.channel().remoteAddress().uncheckedNonnullCast()
    }
}
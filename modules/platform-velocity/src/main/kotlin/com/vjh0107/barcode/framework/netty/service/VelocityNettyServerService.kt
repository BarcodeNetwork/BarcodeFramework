package com.vjh0107.barcode.framework.netty.service

import com.vjh0107.barcode.framework.netty.repository.VelocityNettyServerContextRepository
import com.vjh0107.barcode.framework.utils.toInetSocketAddress
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.util.concurrent.DefaultThreadFactory
import io.netty.util.concurrent.GlobalEventExecutor
import kotlinx.coroutines.*
import org.koin.core.annotation.Single
import org.slf4j.Logger
import java.net.InetSocketAddress
import kotlin.coroutines.CoroutineContext

@Single(binds = [NettyServerService::class])
class VelocityNettyServerService(
    private val logger: Logger,
    private val repository: VelocityNettyServerContextRepository
) : NettyServerService, CoroutineScope {
    private val allChannels: ChannelGroup = DefaultChannelGroup("server", GlobalEventExecutor.INSTANCE)
    private lateinit var parentEventLoopGroup: EventLoopGroup
    private lateinit var childEventLoopGroup: EventLoopGroup

    override val coroutineContext: CoroutineContext = Dispatchers.IO
    private lateinit var job: CoroutineContext

    override fun startServerBootStrap(host: String, port: Int, bootstrap: ServerBootstrap.() -> ServerBootstrap): ServerBootstrap {
        parentEventLoopGroup = NioEventLoopGroup(DefaultThreadFactory("parent"))
        childEventLoopGroup = NioEventLoopGroup(DefaultThreadFactory("child"))
        val serverBootstrap = ServerBootstrap()
        with(serverBootstrap) {
            group(parentEventLoopGroup, childEventLoopGroup)
            channel(NioServerSocketChannel::class.java)
            childOption(ChannelOption.TCP_NODELAY, true)
            childOption(ChannelOption.SO_KEEPALIVE, true)
            bootstrap()
            job = launch {
                runCatching {
                    try {
                        val bindFuture = bind(InetSocketAddress(host, port)).sync()
                        val channel = bindFuture.channel()
                        allChannels.add(channel)
                        bindFuture.channel().closeFuture().sync()
                    } catch (exception: InterruptedException) {
                        throw RuntimeException(exception)
                    } finally {
                        close()
                    }
                }
            }

        }

        return serverBootstrap
    }

    override fun sendMessage(context: ChannelHandlerContext, data: String, log: Boolean) {
        val messageBuffer = Unpooled.buffer()
        messageBuffer.writeBytes(data.toByteArray())
        context.writeAndFlush(messageBuffer)
        if (log) {
            logger.info("message sent to ${context.channel().remoteAddress().toInetSocketAddress().port}: $data")
        }
    }

    override fun sendMessage(targetServer: String, data: String, log: Boolean) {
        val context = repository.findChannelHandlerContextByName(targetServer)
        if (context != null) {
            sendMessage(context, data, log)
        } else {
            logger.error("cannot found server with named $targetServer")
        }
    }

    override fun sendMessageAll(data: String, log: Boolean) {
        if (repository.getAllChannelHandlerContext().isEmpty()) {
            logger.info("attempting to send messages to servers but no servers have registered yet")
        } else {
            repository.getAllChannelHandlerContext().forEach { context ->
                sendMessage(context, data, log)
            }
        }
    }

    override fun close() {
        allChannels.close().awaitUninterruptibly()
        childEventLoopGroup.shutdownGracefully().awaitUninterruptibly()
        parentEventLoopGroup.shutdownGracefully().awaitUninterruptibly()
    }
}
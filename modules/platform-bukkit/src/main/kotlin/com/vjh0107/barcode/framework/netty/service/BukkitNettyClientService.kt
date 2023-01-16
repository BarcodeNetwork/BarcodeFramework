package com.vjh0107.barcode.framework.netty.service

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import org.koin.core.annotation.Single
import java.util.logging.Level
import java.util.logging.Logger

@Single(binds = [NettyClientService::class])
class BukkitNettyClientService(
    private val logger: Logger
) : NettyClientService {
    private lateinit var eventLoopGroup: EventLoopGroup

    private lateinit var clientBootstrap: Bootstrap
    private var context: ChannelHandlerContext? = null

    override suspend fun startBootstrap(host: String, port: Int, bootstrap: Bootstrap.() -> Bootstrap): Bootstrap {
        eventLoopGroup = NioEventLoopGroup()
        clientBootstrap = Bootstrap()
        with(clientBootstrap) {
            group(eventLoopGroup)
            channel(NioSocketChannel::class.java)
            bootstrap()
            runCatching {
                try {
                    val connectFuture = connect(host, port).sync()
                    connectFuture.channel().closeFuture().sync()
                } catch (exception: InterruptedException) {
                    exception.printStackTrace()
                } catch (exception: Exception) {
                    logger.info("proxy server connection error: ${exception.localizedMessage}")
                } finally {
                    close()
                }
            }
        }
        return clientBootstrap
    }

    override fun reconnect(host: String, port: Int) {
        clientBootstrap.connect(host, port)
    }

    override fun setContext(context: ChannelHandlerContext) {
        this.context = context
    }

    override fun sendMessage(data: String) {
        if (this.context != null) {
            val messageBuffer = Unpooled.buffer()
            messageBuffer.writeBytes(data.toByteArray())
            this.context!!.writeAndFlush(messageBuffer)
        } else {
            logger.log(Level.WARNING, "[SocketFramework] channel not yet activate.")
        }
    }

    override fun close() {
        eventLoopGroup.shutdownGracefully()
    }
}
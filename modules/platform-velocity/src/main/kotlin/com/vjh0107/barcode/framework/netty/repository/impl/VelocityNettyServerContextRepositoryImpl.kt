package com.vjh0107.barcode.framework.netty.repository.impl

import com.velocitypowered.api.proxy.server.ServerInfo
import com.vjh0107.barcode.framework.netty.repository.NettyServerContextRepository
import com.vjh0107.barcode.framework.netty.repository.VelocityNettyServerContextRepository
import com.vjh0107.barcode.framework.utils.toInetSocketAddress
import com.vjh0107.barcode.framework.utils.uncheckedNonnullCast
import io.netty.channel.ChannelHandlerContext
import org.koin.core.annotation.Single
import java.net.InetSocketAddress

@Single(binds = [VelocityNettyServerContextRepository::class, NettyServerContextRepository::class])
class VelocityNettyServerContextRepositoryImpl : VelocityNettyServerContextRepository {
    private val servers: MutableMap<ServerInfo, ChannelHandlerContext> = mutableMapOf()

    override fun addContext(key: ServerInfo, context: ChannelHandlerContext) {
        servers[key] = context
    }

    override fun findChannelHandlerContext(key: ServerInfo): ChannelHandlerContext? {
        return servers[key]
    }

    override fun getChannelHandlerContext(key: ServerInfo): ChannelHandlerContext {
        return findChannelHandlerContext(key) ?: throw NullPointerException("channel handler context with $key not found")
    }

    override fun findChannelHandlerContextByName(name: String): ChannelHandlerContext? {
        return servers.filter { it.key.name == name }.values.firstOrNull()
    }

    override fun getChannelHandlerContextByName(name: String): ChannelHandlerContext {
        return findChannelHandlerContextByName(name) ?: throw NullPointerException("channel handler context with named $name not found")
    }

    override fun findChannelHandlerContextByAddress(address: InetSocketAddress): ChannelHandlerContext? {
        return servers.filter { it.key.address == address }.values.firstOrNull()
    }

    override fun getChannelHandlerContextByAddress(address: InetSocketAddress): ChannelHandlerContext {
        return findChannelHandlerContextByAddress(address) ?: throw NullPointerException("channel handler context with address $address not found")
    }

    override fun findChannelHandlerContextByAddressPort(port: Int): ChannelHandlerContext? {
        return servers.values.firstOrNull { it.channel().remoteAddress().uncheckedNonnullCast<InetSocketAddress>().port == port }
    }

    override fun getChannelHandlerContextByAddressPort(port: Int): ChannelHandlerContext {
        return findChannelHandlerContextByAddressPort(port)  ?: throw NullPointerException("channel handler context with remote address port $port not found")
    }

    override fun findRegisteredServerPortByChannelHandlerPort(port: Int): Int? {
        return servers.filter { (_, context) -> context.channel().remoteAddress().toInetSocketAddress().port == port }.keys.firstOrNull()?.address?.port
    }

    override fun getRegisteredServerPortByChannelHandlerPort(port: Int): Int {
        return findRegisteredServerPortByChannelHandlerPort(port) ?: throw NullPointerException("registered server with port $port not found")
    }

    override fun removeContext(key: ServerInfo) {
        servers.remove(key)
    }

    override fun getAllChannelHandlerContext(): List<ChannelHandlerContext> {
        return servers.values.toList()
    }
}
package com.vjh0107.barcode.framework.netty.repository

import com.velocitypowered.api.proxy.server.ServerInfo
import io.netty.channel.ChannelHandlerContext
import org.koin.core.annotation.Single
import java.net.InetSocketAddress

@Single(binds = [NettyServerContextRepository::class])
class VelocityNettyServerContextRepository : NettyServerContextRepository<ServerInfo> {
    private val servers: MutableMap<ServerInfo, ChannelHandlerContext> = mutableMapOf()

    override fun addContext(key: ServerInfo, context: ChannelHandlerContext) {
        servers[key] = context
    }

    override fun findRegisteredServer(key: ServerInfo): ChannelHandlerContext? {
        return servers[key]
    }

    override fun getRegisteredServer(key: ServerInfo): ChannelHandlerContext {
        return findRegisteredServer(key) ?: throw NullPointerException("server with $key not found")
    }

    override fun findRegisteredServerByName(name: String): ChannelHandlerContext? {
        return servers.filter { it.key.name == name }.values.firstOrNull()
    }

    override fun getRegisteredServerByName(name: String): ChannelHandlerContext {
        return findRegisteredServerByName(name) ?: throw NullPointerException("server with named $name not found")
    }

    override fun findRegisteredServerByAddress(address: InetSocketAddress): ChannelHandlerContext? {
        return servers.filter { it.key.address == address }.values.firstOrNull()
    }

    override fun getRegisteredServerByAddress(address: InetSocketAddress): ChannelHandlerContext {
        return findRegisteredServerByAddress(address) ?: throw NullPointerException("server with address $address not found")
    }

    override fun removeContext(key: ServerInfo) {
        servers.remove(key)
    }

    override fun getAllRegisteredServers(): List<ChannelHandlerContext> {
        return servers.values.toList()
    }
}
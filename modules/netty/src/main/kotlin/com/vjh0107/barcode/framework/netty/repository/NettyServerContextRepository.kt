package com.vjh0107.barcode.framework.netty.repository

import io.netty.channel.ChannelHandlerContext
import java.net.InetSocketAddress

interface NettyServerContextRepository<T : Comparable<*>> {

    /**
     * context 를 T 값을 통해 찾습니다.
     */
    fun addContext(key: T, context: ChannelHandlerContext)

    fun findRegisteredServer(key: T): ChannelHandlerContext?
    fun getRegisteredServer(key: T): ChannelHandlerContext
    fun findRegisteredServerByName(name: String): ChannelHandlerContext?
    fun getRegisteredServerByName(name: String): ChannelHandlerContext
    fun findRegisteredServerByAddress(address: InetSocketAddress): ChannelHandlerContext?
    fun getRegisteredServerByAddress(address: InetSocketAddress): ChannelHandlerContext

    /**
     * context 를 삭제합니다.
     */
    fun removeContext(key: T)

    /**
     * 서버에 등록되어있는 모든 서버를 구합니다.
     */
    fun getAllRegisteredServers(): List<ChannelHandlerContext>
}
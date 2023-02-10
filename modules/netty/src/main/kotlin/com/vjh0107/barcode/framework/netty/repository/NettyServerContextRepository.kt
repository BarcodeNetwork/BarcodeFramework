package com.vjh0107.barcode.framework.netty.repository

import io.netty.channel.ChannelHandlerContext
import java.net.InetSocketAddress

interface NettyServerContextRepository<T : Comparable<*>> {

    /**
     * context 를 T 값을 통해 찾습니다.
     */
    fun addContext(key: T, context: ChannelHandlerContext)

    fun findChannelHandlerContext(key: T): ChannelHandlerContext?
    fun getChannelHandlerContext(key: T): ChannelHandlerContext
    fun findChannelHandlerContextByName(name: String): ChannelHandlerContext?
    fun getChannelHandlerContextByName(name: String): ChannelHandlerContext
    fun findChannelHandlerContextByAddress(address: InetSocketAddress): ChannelHandlerContext?
    fun getChannelHandlerContextByAddress(address: InetSocketAddress): ChannelHandlerContext
    fun findChannelHandlerContextByAddressPort(port: Int): ChannelHandlerContext?
    fun getChannelHandlerContextByAddressPort(port: Int): ChannelHandlerContext

    fun findRegisteredServerPortByChannelHandlerPort(port: Int): Int?
    fun getRegisteredServerPortByChannelHandlerPort(port: Int): Int

    /**
     * context 를 삭제합니다.
     */
    fun removeContext(key: T)

    /**
     * 서버에 등록되어있는 모든 서버를 구합니다.
     */
    fun getAllChannelHandlerContext(): List<ChannelHandlerContext>
}
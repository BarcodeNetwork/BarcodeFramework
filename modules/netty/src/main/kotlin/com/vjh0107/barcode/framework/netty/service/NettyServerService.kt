package com.vjh0107.barcode.framework.netty.service

import com.vjh0107.barcode.framework.Closeable
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext

interface NettyServerService : Closeable {
    /**
     * netty 서버 bootstrap 을 시작합니다.
     *
     * @param bootstrap 서버가 시작되기 전, 부트스트랩 설정
     */
    fun startServerBootStrap(host: String, port: Int, bootstrap: ServerBootstrap.() -> ServerBootstrap): ServerBootstrap

    /**
     * string 데이터를 자식 서버에게 전송합니다.
     *
     * @param context 보낼 channel context
     * @param data 전송할 문자열 데이터
     * @param log 로깅 여부
     */
    fun sendMessage(context: ChannelHandlerContext, data: String, log: Boolean = true)

    /**
     * string 데이터를 자식 서버에게 전송합니다.
     *
     * @param targetServer 받을 자식 서버 id
     * @param data 전송할 문자열 데이터
     */
    fun sendMessage(targetServer: String, data: String, log: Boolean = true)

    /**
     * string 데이터를 자식 서버들에게 전송합니다.
     *
     * @param data 전송할 문자열 데이터
     */
    fun sendMessageAll(data: String, log: Boolean = true)

    override fun close()
}
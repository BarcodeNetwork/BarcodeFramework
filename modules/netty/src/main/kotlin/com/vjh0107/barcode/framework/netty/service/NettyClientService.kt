package com.vjh0107.barcode.framework.netty.service

import com.vjh0107.barcode.framework.Closeable
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext

interface NettyClientService : Closeable {
    /**
     * netty bootstrap 을 시작합니다.
     *
     * @param bootstrap 시작하기 전 설정
     */
    suspend fun startBootstrap(host: String, port: Int, bootstrap: Bootstrap.() -> Bootstrap): Bootstrap

    /**
     * 채널 재접속에 사용됩니다.
     * startBootstrap 이 실행되고 난 후 실행되어야 합니다.
     */
    fun reconnect(host: String, port: Int)

    /**
     * 데이터를 부모 서버(프록시) 에게 전송합니다.
     *
     * @param data 전송할 문자열 데이터
     */
    fun sendMessage(data: String)

    /**
     * 메시지를 보낼 handler context 를 설정한다.
     */
    fun setContext(context: ChannelHandlerContext)

    override fun close()
}
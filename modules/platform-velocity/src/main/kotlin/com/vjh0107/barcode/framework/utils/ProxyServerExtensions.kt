package com.vjh0107.barcode.framework.utils

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer

/**
 * 주어진 포트를 통해 Velocity 프록시에 등록되어있는 서버를 구합니다.
 *
 * @param port 프록시에 연결되어 있는 서버의 포트
 * @return Velocity 프록시에 등록되어있는 서버 객체
 * @throws NullPointerException 연결 되어있는 서버가 존재하지 않을 때
 */
fun ProxyServer.getServerByPort(port: Int): RegisteredServer {
    return this.allServers.firstOrNull { it.serverInfo.address.port == port }
        ?: throw NullPointerException("포트 $port 에 연결되어있는 서버가 존재하지 않습니다.")
}

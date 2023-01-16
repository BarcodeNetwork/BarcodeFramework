package com.vjh0107.barcode.framework.proxy.api

import com.vjh0107.barcode.framework.netty.NettyPredefinedMessages

/**
 * 프록시와 자식서버간의 문자열 기반 소켓통신 데이터 클래스입니다.
 *
 * @param eventType 무슨 이벤트가 발동되었는지 문자열을 통해 전달합니다.
 * @param data 객체를 직렬화하여 전달합니다.
 * @param port 서버의 포트입니다. 프록시에서 포트를 통하여 무슨서버인지 구할 수 있습니다.
 */
class ProxyEventData(
    val eventType: String,
    val data: String,
    val port: Int
) {
    companion object {
        fun deserialize(message: String): ProxyEventData {
            val elements = message.split(NettyPredefinedMessages.SEPARATOR.message)
            val eventType = elements[0]
            val data = elements[1]
            val port = elements[2]
            return ProxyEventData(eventType, data, port.toInt())
        }

        fun serialize(eventType: String, data: String, port: Int): String {
            return eventType + NettyPredefinedMessages.SEPARATOR.message + data + NettyPredefinedMessages.SEPARATOR.message + port.toString()
        }

        fun serialize(proxyEventData: ProxyEventData): String {
            return serialize(proxyEventData.eventType, proxyEventData.data, proxyEventData.port)
        }
    }
}
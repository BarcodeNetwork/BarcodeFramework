package com.vjh0107.barcode.framework.proxy.api.event

import com.vjh0107.barcode.framework.netty.NettyPredefinedMessages
import com.vjh0107.barcode.framework.serialization.serialize

data class ProxyEventData(
    override val eventName: String,
    val data: String
) : ProxyEventTypeDescriber {
    companion object {
        inline fun <reified T : ProxyEvent> deserialize(message: String): ProxyEventData {
            val elements = message.split(NettyPredefinedMessages.SEPARATOR.message)
            val eventType = elements[0]
            val data = elements[1]
            return ProxyEventData(eventType, data)
        }

        inline fun <reified T : ProxyEvent> serialize(event: T): String {
            return event.eventName + NettyPredefinedMessages.SEPARATOR.message + event.serialize()
        }
    }
}


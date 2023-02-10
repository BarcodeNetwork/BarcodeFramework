package com.vjh0107.barcode.framework.proxy.api.event

import com.vjh0107.barcode.framework.netty.NettyPredefinedMessages
import com.vjh0107.barcode.framework.serialization.serialize

data class ProxyEventData(
    val describedEventName: String,
    val serializedData: String
) : ProxyEventTypeDescriber {
    companion object {
        fun deserialize(message: String): ProxyEventData {
            val elements = message.split(NettyPredefinedMessages.SEPARATOR.message)
            val eventType = elements[0]
            val data = elements[1]
            return ProxyEventData(eventType, data)
        }

        inline fun <reified T : ProxyEvent> serialize(event: T): String {
            return event.getEventName() + NettyPredefinedMessages.SEPARATOR.message + event.serialize()
        }
    }

    override fun getEventName(): String {
        return describedEventName
    }
}


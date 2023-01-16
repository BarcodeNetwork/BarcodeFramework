package com.vjh0107.barcode.framework.events

import com.vjh0107.barcode.framework.proxy.api.ProxyEventData
import io.netty.channel.ChannelHandlerContext
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ProxyChannelOutboundEvent(
    val context: ChannelHandlerContext,
    val data: ProxyEventData
): Event(true) {
    companion object {
        private var handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
}

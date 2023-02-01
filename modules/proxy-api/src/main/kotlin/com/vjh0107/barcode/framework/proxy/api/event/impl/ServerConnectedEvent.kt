package com.vjh0107.barcode.framework.proxy.api.event.impl

import com.vjh0107.barcode.framework.proxy.api.event.ProxyServerCallEvent
import com.vjh0107.barcode.framework.serialization.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Factory
import java.util.*

@Factory
@Serializable
data class ServerConnectedEvent(
    @Serializable(with = UUIDSerializer::class) val minecraftPlayerUUID: UUID,
    override val server: String,
    val previousServer: String?
) : ProxyServerCallEvent() {
    override val eventName = "ServerConnectedEvent"
}
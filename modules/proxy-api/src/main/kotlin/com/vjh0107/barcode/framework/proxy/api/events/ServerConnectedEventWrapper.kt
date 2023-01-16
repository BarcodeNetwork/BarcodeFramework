package com.vjh0107.barcode.framework.proxy.api.events

import com.vjh0107.barcode.framework.serialization.SerializableData
import com.vjh0107.barcode.framework.serialization.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ServerConnectedEventWrapper(
    @Serializable(with = UUIDSerializer::class) val minecraftPlayerUUID: UUID,
    val server: String,
    val previousServer: String?
) : SerializableData {
    companion object {
        const val id = "ServerConnectedEvent"
    }
}
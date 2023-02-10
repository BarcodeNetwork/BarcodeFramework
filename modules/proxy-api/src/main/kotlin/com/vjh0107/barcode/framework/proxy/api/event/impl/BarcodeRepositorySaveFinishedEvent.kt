package com.vjh0107.barcode.framework.proxy.api.event.impl

import com.vjh0107.barcode.framework.proxy.api.event.ProxyClientCallEvent
import com.vjh0107.barcode.framework.serialization.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Factory
import java.util.*

@Serializable
data class BarcodeRepositorySaveFinishedEvent(
    @Serializable(with = UUIDSerializer::class) val minecraftPlayerUUID: UUID,
    override val serverPort: Int
) : ProxyClientCallEvent
package com.vjh0107.barcode.framework.proxy.api.event.impl

import com.vjh0107.barcode.framework.proxy.api.event.ProxyServerCallEvent
import com.vjh0107.barcode.framework.serialization.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @param minecraftPlayerUUID 저장을 시작할 플레이어 uuid
 */
@Serializable
data class BarcodeRepositorySaveStartEvent(
    @Serializable(with = UUIDSerializer::class) val minecraftPlayerUUID: UUID,
) : ProxyServerCallEvent
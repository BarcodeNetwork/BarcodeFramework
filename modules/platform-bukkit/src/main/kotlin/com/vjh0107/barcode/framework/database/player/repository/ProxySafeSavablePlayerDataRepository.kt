package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.database.player.PlayerIDFactory
import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper
import com.vjh0107.barcode.framework.database.player.data.PlayerData
import com.vjh0107.barcode.framework.database.player.events.BarcodeRepositoryPreSaveEvent
import com.vjh0107.barcode.framework.database.player.getPlayerID
import com.vjh0107.barcode.framework.events.ProxyChannelOutboundEvent
import com.vjh0107.barcode.framework.koin.injector.inject
import com.vjh0107.barcode.framework.netty.service.NettyClientService
import com.vjh0107.barcode.framework.proxy.api.event.ProxyEventData
import com.vjh0107.barcode.framework.proxy.api.event.impl.BarcodeRepositorySaveFinishedEvent
import com.vjh0107.barcode.framework.proxy.api.event.impl.BarcodeRepositorySaveStartEvent
import com.vjh0107.barcode.framework.proxy.api.event.sameAs
import com.vjh0107.barcode.framework.proxy.events.BarcodeRepositorySaveEvent
import com.vjh0107.barcode.framework.serialization.deserialize
import com.vjh0107.barcode.framework.utils.print
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerQuitEvent

abstract class ProxySafeSavablePlayerDataRepository<T : PlayerData>(
    plugin: AbstractBarcodePlugin
) : AbstractSavablePlayerDataRepository<T>(plugin), ProxySafePlayerDataRepository<T> {
    final override suspend fun loadData(id: PlayerIDWrapper): T {
        return loadDataProxySafely(id)
    }

    private val nettyClientService: NettyClientService by inject()

    final override suspend fun saveData(id: PlayerIDWrapper, playerData: T) {
        saveDataProxySafely(id, playerData)
    }

    @EventHandler
    fun onSaveOrderReceived(event: BarcodeRepositorySaveEvent) {
        val id = PlayerIDFactory.getPlayerID(event.player)
        plugin.server.pluginManager.callEvent(BarcodeRepositoryPreSaveEvent(event.player))
        launch {
            unregisterSafe(id)
            if (isFinallySaveCompleted(id)) {
                val eventData = BarcodeRepositorySaveFinishedEvent(id.minecraftPlayerID.id, plugin.server.port)
                val serializedEventData = ProxyEventData.serialize(eventData)
                nettyClientService.sendMessage(serializedEventData)
            }
        }
    }

    /**
     * 서버 간 이동이 아닌, 프록시 서버를 나갔을 때 저장합니다.
     * 서버 간 이동이 아닌 프록시 서버를 나갔을 때를 판별하는 방법은, 플레이어의 id를 통해 getPlayerData(id) 를 호출하였을 때,
     * data 가 정리되지 않은 경우에, 프록시 서버를 나갔을 때로 판별하고 저장합니다.
     */
    @EventHandler
    fun onQuitProxy(event: PlayerQuitEvent) {
        val id = PlayerIDFactory.getPlayerID(event.player)
        plugin.server.pluginManager.callEvent(BarcodeRepositoryPreSaveEvent(event.player))
        val data: T? = getPlayerData(id)
        if (data != null) {
            launch {
                unregisterSafe(id)
            }
        }
    }
}
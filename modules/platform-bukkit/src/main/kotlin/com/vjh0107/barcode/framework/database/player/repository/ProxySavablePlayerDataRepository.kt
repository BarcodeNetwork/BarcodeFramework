package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper
import com.vjh0107.barcode.framework.database.player.data.PlayerData
import com.vjh0107.barcode.framework.database.player.getPlayerID
import com.vjh0107.barcode.framework.events.ProxyChannelOutboundEvent
import com.vjh0107.barcode.framework.proxy.api.events.ServerConnectedEventWrapper
import com.vjh0107.barcode.framework.serialization.deserialize
import kotlinx.coroutines.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

abstract class ProxySavablePlayerDataRepository<T : PlayerData>(plugin: AbstractBarcodePlugin) : AbstractSavablePlayerDataRepository<T>(plugin) {
    private val coroutineJobs: MutableMap<PlayerIDWrapper, Job> = mutableMapOf()

    @EventHandler
    fun onChat(event: ProxyChannelOutboundEvent) {
        if (event.data.eventType == ServerConnectedEventWrapper.id) {
            val connectedUUID = event.data.data.deserialize<ServerConnectedEventWrapper>().minecraftPlayerUUID
            val connectedPlayer = plugin.server.getPlayer(connectedUUID) ?: throw NullPointerException("$connectedUUID uuid 를 가진 플레이어는 접속중이 아닙니다.")
            noticeLoadPlayerInventoryData(connectedPlayer)
        }
    }

    abstract suspend fun loadDataProxySafely(id: PlayerIDWrapper): T

    private fun noticeLoadPlayerInventoryData(player: Player) {
        val id = player.getPlayerID()
        val job = coroutineJobs[id] ?: throw RuntimeException("데이터 로드중이 아닙니다.")
        job.cancel()
        coroutineJobs.remove(id)
    }

    private suspend fun lockLoadData(id: PlayerIDWrapper) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            plugin.logger.severe(
                "프록시가 프로파일 ${id.profileID} 의 데이터의 로드시점을 명시적으로 전달하지 않았습니다." +
                        " 이는 프록시와의 통신이 1000ms 보다 오래 걸리기 때문에 발생한 문제일 수 있으며" +
                        " 데이터베이스의 무결성을 보장할 수 없습니다."
            )
            cancel()
        }
        coroutineJobs[id] = job
        job.join()
    }


    final override suspend fun loadData(id: PlayerIDWrapper): T {
        lockLoadData(id)
        return loadDataProxySafely(id)
    }
}
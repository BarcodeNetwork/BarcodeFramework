package com.vjh0107.barcode.framework.transaction.listeners.impl

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import com.velocitypowered.api.proxy.Player
import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.events.ProxyChannelInboundEvent
import com.vjh0107.barcode.framework.netty.service.NettyServerService
import com.vjh0107.barcode.framework.proxy.api.event.ProxyEventData
import com.vjh0107.barcode.framework.proxy.api.event.impl.BarcodeRepositorySaveFinishedEvent
import com.vjh0107.barcode.framework.proxy.api.event.impl.BarcodeRepositorySaveStartEvent
import com.vjh0107.barcode.framework.proxy.api.event.sameAs
import com.vjh0107.barcode.framework.serialization.deserialize
import com.vjh0107.barcode.framework.transaction.listeners.PlayerTransactionListener
import com.vjh0107.barcode.framework.transaction.repository.PlayerTransactionAllowedRepository
import com.vjh0107.barcode.framework.transaction.service.PlayerTransactionCoroutineLockService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.slf4j.Logger
import kotlin.coroutines.CoroutineContext

@Suppress("UnstableApiUsage")
@BarcodeComponent
class PlayerTransactionListenerImpl(
    private val logger: Logger,
    private val coroutineLockService: PlayerTransactionCoroutineLockService,
    private val nettyService: NettyServerService,
    private val allowedPlayerRepository: PlayerTransactionAllowedRepository,
    private val plugin: AbstractBarcodePlugin
) : BarcodeListener, PlayerTransactionListener, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val pluginConfigTimeout = plugin
        .getConfig("config.yml")
        .getNode("player-transaction-timeout")
        .getLong(1000L)

    @Subscribe
    override fun onLogin(event: LoginEvent) {
        allowedPlayerRepository.add(event.player.uniqueId)
    }

    private fun callSaveEvent(player: Player) {
        val saveEvent = BarcodeRepositorySaveStartEvent(player.uniqueId)
        val previousServer = player.currentServer
        nettyService.sendMessage(previousServer.get().serverInfo.name, ProxyEventData.serialize(saveEvent))
    }

    @Subscribe
    override fun onPlayerPreConnect(event: ServerPreConnectEvent) {
        if (allowedPlayerRepository.isAllowed(event.player.uniqueId)) {
            allowedPlayerRepository.remove(event.player.uniqueId)
            return
        }

        event.result = ServerPreConnectEvent.ServerResult.denied()

        launch parent@{
            callSaveEvent(event.player)
            coroutineLockService.tryLock(event.player.uniqueId, pluginConfigTimeout) whenTimedOut@{
                event.player.sendMessage(Component.text("§c데이터 저장에 실패하였습니다. 서버 접속을 허가하지 않습니다."))
                logger.error("${event.player.currentServer.get().serverInfo.name} 서버에서 ${event.player.uniqueId} 플레이어의 데이터 저장이 실패하였습니다.")
                this@parent.cancel()
            }
            allowedPlayerRepository.add(event.player.uniqueId)
            if (!plugin.ignoringInfoMessages) {
                logger.info("create connection request to ${event.originalServer.serverInfo.name}")
            }
            event.player.createConnectionRequest(event.originalServer).fireAndForget()
        }
    }

    @Subscribe
    override fun onPlayerReceiveDataSaveFinished(event: ProxyChannelInboundEvent) {
        if (event.eventData.sameAs<BarcodeRepositorySaveFinishedEvent>()) {
            val parsedEvent = event.eventData.serializedData.deserialize<BarcodeRepositorySaveFinishedEvent>()
            val playerId = parsedEvent.minecraftPlayerUUID
            coroutineLockService.releaseLock(playerId)
        }
    }
}
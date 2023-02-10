package com.vjh0107.barcode.framework.transaction.listeners

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import com.vjh0107.barcode.framework.events.ProxyChannelInboundEvent

interface PlayerTransactionListener {

    fun onPlayerPreConnect(event: ServerPreConnectEvent)

    fun onLogin(event: LoginEvent)

    fun onPlayerReceiveDataSaveFinished(event: ProxyChannelInboundEvent)
}
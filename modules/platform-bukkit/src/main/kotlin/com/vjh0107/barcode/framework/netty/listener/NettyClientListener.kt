package com.vjh0107.barcode.framework.netty.listener

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeListener
import com.vjh0107.barcode.framework.netty.service.NettyClientService
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

@BarcodeComponent
class NettyClientListener(
    private val plugin: AbstractBarcodePlugin,
    private val nettyService: NettyClientService
) : BarcodeListener {
    @EventHandler
    fun onPlayerLogin(event: AsyncPlayerPreLoginEvent) {
        if (plugin.config.getBoolean("netty.enabled", false)) {
            if (nettyService.getContext() == null) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Netty ChannelContextHandler 가 아직 초기화되지 않았습니다."))
            }
        }
    }
}
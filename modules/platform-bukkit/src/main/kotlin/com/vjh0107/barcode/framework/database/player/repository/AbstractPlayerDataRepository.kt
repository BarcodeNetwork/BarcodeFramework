package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeRepository
import com.vjh0107.barcode.framework.database.player.PlayerIDFactory
import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper
import com.vjh0107.barcode.framework.database.player.data.PlayerData
import com.vjh0107.barcode.framework.exceptions.playerdata.PlayerDataNotFoundException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import kotlin.coroutines.CoroutineContext

@Suppress("LeakingThis")
abstract class AbstractPlayerDataRepository<T : PlayerData>(
    val plugin: AbstractBarcodePlugin
) : PlayerDataRepository<T>, BarcodeRepository, Listener, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    protected val dataMap: MutableMap<PlayerIDWrapper, T> = Collections.synchronizedMap(HashMap())

    open fun onLoad() {}

    final override fun load() {
        plugin.registerListener(this)
        registerRepository(this)
        onLoad()
    }

    override fun getPlayerData(playerID: PlayerIDWrapper): T? {
        return dataMap[playerID]
    }

    open override suspend fun unregisterSafe(id: PlayerIDWrapper) {
        getPlayerData(id)?.close() ?: throw PlayerDataNotFoundException(id)
        dataMap.remove(id)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        launch {
            setup(PlayerIDFactory.getPlayerID(event.player))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(event: PlayerQuitEvent) {
        if (this is ProxySafePlayerDataRepository<*>) {
            return
        }
        val id = PlayerIDFactory.getPlayerID(event.player)
        launch {
            unregisterSafe(id)
        }
    }

    open fun onClose() {}

    final override fun close() {
        removeRepository(this)
        onClose()
    }

    protected companion object RegisteredRepositoriesImpl : RegisteredRepositories {
        private val repositories: MutableList<PlayerDataRepository<out PlayerData>> = mutableListOf()
        private val saveFinishedRepositories: MutableMap<PlayerIDWrapper, Int> = Collections.synchronizedMap(HashMap())

        @Synchronized
        override fun isFinallySaveCompleted(id: PlayerIDWrapper): Boolean {
            addFinishedRepositoryCount(id)
            return if (repositories.size == saveFinishedRepositories[id]) {
                flushFinishedRepositoryCount(id)
                true
            } else {
                false
            }
        }

        private fun addFinishedRepositoryCount(id: PlayerIDWrapper) {
            val finishes = saveFinishedRepositories[id]
            if (finishes == null) {
                saveFinishedRepositories[id] = 1
            } else {
                saveFinishedRepositories[id] = finishes + 1
            }
        }

        private fun flushFinishedRepositoryCount(id: PlayerIDWrapper) {
            saveFinishedRepositories.remove(id)
        }

        private fun <T : PlayerData> registerRepository(repository: PlayerDataRepository<T>) {
            repositories.add(repository)
        }

        private fun <T : PlayerData> removeRepository(repository: PlayerDataRepository<T>) {
            repositories.remove(repository)
        }
    }
}
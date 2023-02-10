package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.LoggerProvider
import com.vjh0107.barcode.framework.database.datasource.BarcodeDataSource
import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper
import com.vjh0107.barcode.framework.database.player.data.PlayerData
import com.vjh0107.barcode.framework.database.player.events.BarcodeRepositoryDataLoadEvent
import com.vjh0107.barcode.framework.database.player.getPlayer
import com.vjh0107.barcode.framework.exceptions.playerdata.PlayerDataNotFoundException
import com.vjh0107.barcode.framework.koin.injector.inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.koin.core.parameter.parametersOf
import java.util.logging.Logger

/**
 * BarcodeRepository 입니다.
 * 가장 먼저 초기화되는 컴포넌트 입니다.
 */
abstract class AbstractSavablePlayerDataRepository<T : PlayerData>(
    plugin: AbstractBarcodePlugin
) : AbstractPlayerDataRepository<T>(plugin), SavablePlayerDataRepository<T>, LoggerProvider {
    val dataSource: BarcodeDataSource by inject { parametersOf(plugin) }

    final override fun onClose() {
        dataSource.close()
        getLogger().info("BarcodeDataSource 를 성공적으로 닫았습니다.")
    }

    abstract fun getTablesToLoad(): List<Table>

    final override fun onLoad() {
        CoroutineScope(Dispatchers.IO).launch {
            dataSource.query {
                SchemaUtils.createMissingTablesAndColumns(*getTablesToLoad().toTypedArray(), withLogs = false)
            }
        }
    }

    final override suspend fun setup(id: PlayerIDWrapper) {
        if (!dataMap.containsKey(id)) {
            val data = loadData(id)
            dataMap[id] = data
            Bukkit.getPluginManager().callEvent(BarcodeRepositoryDataLoadEvent(id.getPlayer(), data))
        }
    }

    final override suspend fun unregisterSafe(id: PlayerIDWrapper) {
        val playerData = getPlayerData(id) ?: throw PlayerDataNotFoundException(id)
        if (dataMap.containsKey(id)) {
            saveData(id, playerData)
            playerData.close()
            this@AbstractSavablePlayerDataRepository.dataMap.remove(id)
        } else {
            playerData.close()
            this.dataMap.remove(id)
        }
    }

    open override fun getLogger(): Logger {
        return plugin.logger
    }
}


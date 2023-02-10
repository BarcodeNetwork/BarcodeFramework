package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper
import com.vjh0107.barcode.framework.database.player.data.PlayerData

interface ProxySafePlayerDataRepository<T : PlayerData> : PlayerDataRepository<T> {
    suspend fun loadDataProxySafely(id: PlayerIDWrapper): T

    suspend fun saveDataProxySafely(id: PlayerIDWrapper, data: T)
}
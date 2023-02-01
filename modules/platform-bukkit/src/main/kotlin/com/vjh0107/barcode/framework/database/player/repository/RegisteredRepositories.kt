package com.vjh0107.barcode.framework.database.player.repository

import com.vjh0107.barcode.framework.database.player.PlayerIDWrapper

interface RegisteredRepositories {
    fun isFinallySaveCompleted(id: PlayerIDWrapper): Boolean
}
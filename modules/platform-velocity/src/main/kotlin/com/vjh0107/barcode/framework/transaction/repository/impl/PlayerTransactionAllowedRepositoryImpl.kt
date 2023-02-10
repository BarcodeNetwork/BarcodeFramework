package com.vjh0107.barcode.framework.transaction.repository.impl

import com.vjh0107.barcode.framework.transaction.repository.PlayerTransactionAllowedRepository
import org.koin.core.annotation.Single
import java.util.*

@Single(binds = [PlayerTransactionAllowedRepository::class])
class PlayerTransactionAllowedRepositoryImpl : PlayerTransactionAllowedRepository {
    private val allowedPlayers: MutableList<UUID> = mutableListOf()

    override fun add(playerId: UUID) {
        allowedPlayers.add(playerId)
    }

    override fun isAllowed(playerId: UUID): Boolean {
        return allowedPlayers.contains(playerId)
    }

    override fun remove(playerId: UUID) {
        allowedPlayers.remove(playerId)
    }

    override fun clear() {
        allowedPlayers.clear()
    }
}
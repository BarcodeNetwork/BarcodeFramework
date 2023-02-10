package com.vjh0107.barcode.framework.transaction.repository

import java.util.UUID

interface PlayerTransactionAllowedRepository {
    fun add(playerId: UUID)

    fun isAllowed(playerId: UUID): Boolean

    fun remove(playerId: UUID)

    fun clear()
}
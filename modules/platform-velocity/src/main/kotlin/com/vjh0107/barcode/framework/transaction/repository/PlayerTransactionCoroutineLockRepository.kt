package com.vjh0107.barcode.framework.transaction.repository

import kotlinx.coroutines.Job
import java.util.UUID

interface PlayerTransactionCoroutineLockRepository {
    fun addLock(playerId: UUID, lock: Job)

    fun getLock(playerId: UUID): Job

    fun removeLock(playerId: UUID)

    fun clear()
}
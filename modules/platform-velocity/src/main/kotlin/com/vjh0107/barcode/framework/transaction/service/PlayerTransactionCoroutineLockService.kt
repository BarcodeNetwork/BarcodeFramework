package com.vjh0107.barcode.framework.transaction.service

import kotlinx.coroutines.CoroutineScope
import java.util.*

interface PlayerTransactionCoroutineLockService {
    suspend fun tryLock(playerId: UUID, timeout: Long, whenTimedOut: CoroutineScope.() -> Unit)

    fun releaseLock(playerId: UUID)
}
package com.vjh0107.barcode.framework.transaction.repository.impl

import com.vjh0107.barcode.framework.transaction.repository.PlayerTransactionCoroutineLockRepository
import kotlinx.coroutines.*
import org.koin.core.annotation.Single
import org.slf4j.Logger
import java.util.*

@Single(binds = [PlayerTransactionCoroutineLockRepository::class])
class PlayerTransactionCoroutineLockRepositoryImpl(
    private val logger: Logger
) : PlayerTransactionCoroutineLockRepository {
    private val playerCoroutineLocks: MutableMap<UUID, Job> = mutableMapOf()

    override fun addLock(playerId: UUID, lock: Job) {
        playerCoroutineLocks[playerId] = lock
    }

    override fun getLock(playerId: UUID): Job {
        return playerCoroutineLocks[playerId] ?: throw NullPointerException("$playerId 의 lock 이 없습니다.")
    }

    override fun removeLock(playerId: UUID) {
        playerCoroutineLocks.remove(playerId)
    }

    override fun clear() {
        playerCoroutineLocks.clear()
    }
}
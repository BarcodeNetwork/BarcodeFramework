package com.vjh0107.barcode.framework.transaction.service.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.transaction.repository.PlayerTransactionCoroutineLockRepository
import com.vjh0107.barcode.framework.transaction.service.PlayerTransactionCoroutineLockService
import kotlinx.coroutines.*
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.slf4j.Logger
import java.util.UUID

@Single(binds = [PlayerTransactionCoroutineLockService::class])
class PlayerTransactionCoroutineLockServiceImpl(
    private val logger: Logger,
    private val repository: PlayerTransactionCoroutineLockRepository
) : PlayerTransactionCoroutineLockService {
    override suspend fun tryLock(playerId: UUID, timeout: Long, whenTimedOut: CoroutineScope.() -> Unit) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(timeout)
            logger.error("프록시가 자식 서버로부터 데이터 로드가 완료됐음을 전달받지 못하였습니다.")
            whenTimedOut()
            cancel()
        }
        repository.addLock(playerId, job)
        job.join()
    }

    override fun releaseLock(playerId: UUID) {
        repository.getLock(playerId).cancel()
        repository.removeLock(playerId)
    }
}
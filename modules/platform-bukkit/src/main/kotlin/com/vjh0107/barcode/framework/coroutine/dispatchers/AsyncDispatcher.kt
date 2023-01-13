package com.vjh0107.barcode.framework.coroutine.dispatchers

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.DisposableCoroutineDispatcher
import com.vjh0107.barcode.framework.coroutine.service.WakeUpBlockService
import com.vjh0107.barcode.framework.koin.injector.inject
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Named
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

@Factory(binds = [DisposableCoroutineDispatcher::class])
@Named("async")
class AsyncDispatcher(
    @InjectedParam private val plugin: AbstractBarcodePlugin
) : DisposableCoroutineDispatcher() {
    private val wakeUpBlockService: WakeUpBlockService by inject { parametersOf(plugin) }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        wakeUpBlockService.ensureWakeup()
        return plugin.server.isPrimaryThread
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!plugin.isEnabled) {
            return
        }
        plugin.server.scheduler.runTaskAsynchronously(plugin, block)
    }

    override fun dispose() {
        wakeUpBlockService.dispose()
    }
}
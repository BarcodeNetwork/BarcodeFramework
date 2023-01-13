package com.vjh0107.barcode.framework.coroutine.dispatchers

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.DisposableCoroutineDispatcher
import com.vjh0107.barcode.framework.coroutine.service.WakeUpBlockService
import com.vjh0107.barcode.framework.coroutine.elements.CoroutineTimingsElement
import com.vjh0107.barcode.framework.koin.injector.inject
import kotlinx.coroutines.CoroutineDispatcher
import org.bukkit.plugin.Plugin
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Named
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

@Factory(binds = [DisposableCoroutineDispatcher::class])
@Named("main")
class MainDispatcher(
    @InjectedParam private val plugin: AbstractBarcodePlugin
) : DisposableCoroutineDispatcher() {
    private val wakeUpBlockService: WakeUpBlockService by inject { parametersOf(plugin) }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        wakeUpBlockService.ensureWakeup()
        return !plugin.server.isPrimaryThread
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!plugin.isEnabled) {
            return
        }

        val timedRunnable = context[CoroutineTimingsElement.Key]

        if (timedRunnable == null) {
            plugin.server.scheduler.runTask(plugin, block)
            return
        }

        timedRunnable.queue.add(block)
        plugin.server.scheduler.runTask(plugin, timedRunnable)
    }

    override fun dispose() {
        wakeUpBlockService.dispose()
    }
}
package com.vjh0107.barcode.framework.coroutine.service.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.plugin.CoroutinePlugin
import com.vjh0107.barcode.framework.coroutine.repository.CoroutinePluginRepository
import com.vjh0107.barcode.framework.coroutine.service.CoroutinePluginService
import org.koin.core.annotation.Single

@Single(binds = [CoroutinePluginService::class])
class CoroutinePluginServiceImpl(private val repository: CoroutinePluginRepository) : CoroutinePluginService {
    override fun getCoroutinePlugin(plugin: AbstractBarcodePlugin): CoroutinePlugin {
        return repository.getCoroutinePlugin(plugin)
    }

    override fun closeCoroutinePlugin(plugin: AbstractBarcodePlugin) {
        with(repository) {
            removeCoroutinePlugin(plugin)
            getCoroutinePlugin(plugin).let {
                it.mainDispatcher.dispose()
                it.asyncDispatcher.dispose()
            }
        }
    }
}
package com.vjh0107.barcode.framework.coroutine.repository.impl

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.coroutine.plugin.CoroutinePlugin
import com.vjh0107.barcode.framework.coroutine.repository.CoroutinePluginRepository
import com.vjh0107.barcode.framework.koin.injector.inject
import org.koin.core.annotation.Single
import org.koin.core.parameter.parametersOf

@Single(binds = [CoroutinePluginRepository::class])
class CoroutinePluginRepositoryImpl : CoroutinePluginRepository {
    private val pluginSessions: MutableMap<AbstractBarcodePlugin, CoroutinePlugin> = mutableMapOf()

    override fun getCoroutinePlugin(plugin: AbstractBarcodePlugin): CoroutinePlugin {
        if (!pluginSessions.containsKey(plugin)) {
            val coroutinePlugin: CoroutinePlugin by inject { parametersOf(plugin) }
            pluginSessions[plugin] = coroutinePlugin
        }

        return pluginSessions[plugin]!!
    }

    override fun removeCoroutinePlugin(plugin: AbstractBarcodePlugin) {
        pluginSessions.remove(plugin)
    }
}
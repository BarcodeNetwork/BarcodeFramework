package com.vjh0107.barcode.framework

import com.google.inject.Inject
import com.velocitypowered.api.command.CommandManager
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.plugin.PluginManager
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.vjh0107.barcode.framework.component.handler.VelocityComponentHandlerModule
import com.vjh0107.barcode.framework.koin.initKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.slf4j.Logger
import java.nio.file.Path

@Plugin(
    id = "barcodeframework",
    name = "BarcodeFramework",
    description = "",
    version = "1.0.0",
    url = "https://github.com/vjh0107",
    authors = ["vjh0107"]
)
class BarcodeFrameworkPlugin @Inject constructor(
    override val server: ProxyServer,
    val logger: Logger,
    override val container: PluginContainer,
    @DataDirectory override val configDirectory: Path
) : AbstractBarcodePlugin() {
    init { initVelocityPlatformKoin() }
    private fun initVelocityPlatformKoin() {
        initKoin().modules(module {
            single<Logger> { this@BarcodeFrameworkPlugin.logger }
            single<ProxyServer> { server }
            single<PluginContainer> (named("barcodeframework")) { container }
            single<CommandManager> { server.commandManager }
            single<EventManager> { server.eventManager }
            single<PluginManager> { server.pluginManager }
            includes(VelocityComponentHandlerModule().module)
        })
    }

    override fun getLogger(): java.util.logging.Logger {
        return java.util.logging.Logger.getLogger(logger.name)
    }
}
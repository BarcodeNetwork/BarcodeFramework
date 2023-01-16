package com.vjh0107.barcode.framework

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import com.vjh0107.barcode.framework.component.handler.ComponentHandlers
import com.vjh0107.barcode.framework.koin.KoinContextualApplication
import com.vjh0107.barcode.framework.koin.injector.inject
import com.vjh0107.barcode.framework.utils.print
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader
import org.koin.core.parameter.parametersOf
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


abstract class AbstractBarcodePlugin : KoinContextualApplication {
    abstract val server: ProxyServer
    abstract val container: PluginContainer
    abstract val configDirectory: Path

    private val componentHandlers: ComponentHandlers by inject { parametersOf(this) }
    private val configs: MutableMap<String, ConfigurationNode> = mutableMapOf()

    val ignoringInfoMessages: Boolean
        get() {
            return getConfig("config.yml").getNode("ignoringInfoMessages").getBoolean(false)
        }

    fun <T> registerListener(listener: T) {
        server.eventManager.register(this, listener)
    }

    @Subscribe(order = PostOrder.FIRST)
    fun onEnableProxy(event: ProxyInitializeEvent) {
        componentHandlers.get().forEach {
            it.onEnable()
        }
        this.getLogger().info("\u001B[33m" + "성공적으로 플러그인이 로드되었습니다." + "\u001B[0m")
    }

    @Subscribe
    fun onDisableProxy(event: ProxyShutdownEvent) {
        componentHandlers.get().forEach {
            it.onDisable()
        }
    }

    /**
     * config 를 가져옵니다. 만약 config 를 한번도 로드한 적 없다면, 로드합니다.
     */
    fun getConfig(name: String): ConfigurationNode {
        return configs[name] ?: createConfigLoader(resolveConfig(name)).load()
    }

    private fun createConfigLoader(path: Path): YAMLConfigurationLoader {
        return YAMLConfigurationLoader.builder().setPath(path).build();
    }

    private fun resolveConfig(fileName: String): Path {
        val configFile: Path = configDirectory.resolve(fileName)

        if (!Files.exists(configFile)) {
            try {
                Files.createDirectories(configFile.getParent())
            } catch (e: IOException) {
            }
            try {
                javaClass.classLoader.getResourceAsStream(fileName)
                    .use { inputStream -> Files.copy(inputStream!!, configFile) }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        return configFile
    }
}
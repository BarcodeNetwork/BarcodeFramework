package com.vjh0107.barcode.framework.utils.config

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import kotlinx.coroutines.*
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * 코루틴을 이용한 idiomatic 한 Bukkit Config 입니다.
 */
data class BarcodePluginConfig(
    private val plugin: AbstractBarcodePlugin,
    val path: String = "",
    val name: String,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {
    private var config = loadConfigAsync()

    fun reload() {
        config = loadConfigAsync()
    }

    suspend fun getConfig(): YamlConfiguration {
        return config.await()
    }

    private fun loadConfigAsync(): Deferred<YamlConfiguration> {
        return async { loadConfig() }
    }

    private fun loadConfig(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(File(plugin.dataFolder.toString() + path, "$name.yml"))
    }

    fun save() = launch {
        kotlin.runCatching {
            getConfig().save(File(plugin.dataFolder.toString() + path, "$name.yml"))
        }
    }

    fun setup() {
        launch {
            kotlin.runCatching {
                if (!File(plugin.dataFolder.toString() + path).exists()) {
                    File(plugin.dataFolder.toString() + path).mkdir()
                }

                if (!File(plugin.dataFolder.toString() + path, "$name.yml").exists()) {
                    File(plugin.dataFolder.toString() + path, "$name.yml").createNewFile()
                }
            }
        }
    }
}
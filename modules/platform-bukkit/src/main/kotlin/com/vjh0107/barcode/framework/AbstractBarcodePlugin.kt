package com.vjh0107.barcode.framework

import com.vjh0107.barcode.framework.component.handler.ComponentHandlers
import com.vjh0107.barcode.framework.coroutine.elements.CoroutinePluginElement
import com.vjh0107.barcode.framework.coroutine.elements.CoroutineTimingsElement
import com.vjh0107.barcode.framework.coroutine.MinecraftMain
import com.vjh0107.barcode.framework.database.config.DatabaseHost
import com.vjh0107.barcode.framework.database.config.DatabaseHostProvider
import com.vjh0107.barcode.framework.events.BarcodePluginDisableEvent
import com.vjh0107.barcode.framework.exceptions.KeyNotFoundException
import com.vjh0107.barcode.framework.injection.instance.injector.InjectorFactory
import com.vjh0107.barcode.framework.koin.injector.inject
import com.vjh0107.barcode.framework.utils.config.BarcodePluginConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.kyori.adventure.text.Component
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.fusesource.jansi.Ansi
import org.koin.core.parameter.parametersOf
import java.io.File
import kotlin.coroutines.CoroutineContext

abstract class AbstractBarcodePlugin : JavaPlugin(), BarcodeApplication, DatabaseHostProvider, CoroutineScope {
    /**
     * componenthandler 들은 barcodecomponent 들의 객체 생성을 관리합니다.
     * 또한 객체의 생성과 함께 생성자주입을 지원합니다.
     */
    private val componentHandlers: ComponentHandlers by inject { parametersOf(this) }

    fun <LISTENER : Listener> registerListener(listener: LISTENER) {
        this.server.pluginManager.registerEvents(listener, this)
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.MinecraftMain(this) + object : CoroutineTimingsElement() {} + CoroutinePluginElement(this) + CoroutineExceptionHandler { context, throwable ->
            logger.severe("잡히지 않은 예외가 발생하였습니다. CoroutineContext: $context")
        }

    /**
     * @see com.vjh0107.barcode.framework.injection.instance.InjectInstance
     */
    final override fun onLoad() {
        onPreLoad()
        InjectorFactory.getPluginInstanceInjector().inject(this)
        onPostLoad()
    }

    final override fun onEnable() {
        onPreEnable()
        componentHandlers.get().forEach {
            it.onEnable()
        }
        this.logger.info(Ansi.ansi().fgBright(Ansi.Color.YELLOW).boldOff().toString() + "성공적으로 플러그인이 로드되었습니다.")
        onPostEnable()
    }

    final override fun onDisable() {
        server.onlinePlayers.forEach {
            it.kick(Component.text("§6서버가 종료되었습니다."))
        }
        onPreDisable()
        componentHandlers.get().forEach {
            it.onDisable()
        }
        BarcodePluginDisableEvent(this).callEvent()
        onPostDisable()
    }

    /**
     * componenthandler들을 전부 리로드함으로써 플러그인이 리로드됩니다.
     */
    fun reloadPlugin() {
        componentHandlers.get().forEach {
            it.onReload()
        }
    }

    public override fun getFile(): File {
        return super.getFile()
    }

    override fun getDatabaseHost(): DatabaseHost {
        return try {
            val databaseSection = config.getConfigurationSection("database") ?: throw KeyNotFoundException()
            val address = databaseSection.getString("address") ?: throw KeyNotFoundException()
            val port = databaseSection.getString("port") ?: throw KeyNotFoundException()
            val databaseName = databaseSection.getString("databaseName") ?: throw KeyNotFoundException()
            val user = databaseSection.getString("user") ?: throw KeyNotFoundException()
            val password = databaseSection.getString("password") ?: throw KeyNotFoundException()
            val poolName = this.name
            DatabaseHost(address, port, user, password, databaseName, poolName)
        } catch (exception: KeyNotFoundException) {
            logger.severe("config.yml 를 설정해주세요. 'database.(host, port, databaseName, user, password)'")
            pluginLoader.disablePlugin(this)
            throw exception
        }
    }

    fun loadConfig(path: String = "", name: String, dispatcher: CoroutineContext = this.coroutineContext): BarcodePluginConfig {
        return BarcodePluginConfig(this, path, name, dispatcher)
    }
}
package com.vjh0107.barcode.framework.component.handler.impl.registrable

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeRegistrable
import com.vjh0107.barcode.framework.component.handler.AbstractBukkitComponentHandler
import com.vjh0107.barcode.framework.component.handler.BarcodeComponentHandler
import com.vjh0107.barcode.framework.exceptions.DuplicateKeyException
import org.bukkit.Bukkit
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSubclassOf

@BarcodeComponentHandler
@Factory(binds = [AbstractBukkitComponentHandler::class])
@Named("BarcodeRegistrableHandler")
class BarcodeRegistrableHandler<P: AbstractBarcodePlugin>(
    plugin: P
) : AbstractBukkitComponentHandler<P, BarcodeRegistrable>(plugin) {
    private val registrableMap: MutableMap<String, BarcodeRegistrable> = mutableMapOf()

    override fun validate(clazz: KClass<BarcodeRegistrable>): Boolean {
        return clazz.isSubclassOf(BarcodeRegistrable::class)
    }

    override fun onPostEnable() {
        with(this.getComponents()) {
            forEach { registrable ->
                registrable.preLoad()
            }
            forEach { registrable ->
                if (registrableMap.containsKey(registrable.id)) {
                    throw DuplicateKeyException(registrable.id)
                }
                registrableMap[registrable.id] = registrable
                registerRegistrars(registrable)
            }
            forEach { registrable ->
                registrable.load()
            }
        }
    }

    override fun onDisable() {
        registrableMap.forEach { (_, registrable) ->
            registrable.close()
        }
        registrableMap.forEach { (_, registrable) ->
            unregisterUnRegistrars(registrable)
        }
        registrableMap.clear()
        plugin.logger.info("Registrable 을 성공적으로 닫았습니다.")
    }

    private fun registerRegistrars(registrable: BarcodeRegistrable) {
        registrable::class.declaredFunctions.forEach { kFunction ->
            kFunction.annotations.forEach { annotation ->
                if (annotation is Registrar) {
                    if (annotation.depend == "null") {
                        kFunction.call(registrable)
                    } else if (Bukkit.getPluginManager().getPlugin(annotation.depend) != null) {
                        kFunction.call(registrable)
                    } else {
                        plugin.logger.info("플러그인 ${annotation.depend} 을 찾지 못하여 " +
                                "Registrable ${registrable.id} 의 Registrar ${kFunction.name} 가 실행되지 않았습니다.")
                    }
                }
            }
        }
    }

    private fun unregisterUnRegistrars(registrable: BarcodeRegistrable) {
        registrable::class.declaredFunctions.forEach { kFunction ->
            kFunction.annotations.forEach { annotation ->
                if (annotation is UnRegistrar) {
                    if (annotation.pluginName == "null") {
                        kFunction.call(registrable)
                    } else if (Bukkit.getPluginManager().getPlugin(annotation.pluginName) != null) {
                        kFunction.call(registrable)
                    } else {
                        plugin.logger.info("플러그인 ${annotation.pluginName} 을 찾지 못하여 " +
                                "Registrable ${registrable.id} 의 UnRegistrar ${kFunction.name} 가 실행되지 않았습니다.")
                    }
                }
            }
        }
    }
}
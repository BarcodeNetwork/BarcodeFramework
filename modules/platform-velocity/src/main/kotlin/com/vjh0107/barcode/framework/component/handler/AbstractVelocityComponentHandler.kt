package com.vjh0107.barcode.framework.component.handler

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.IBarcodeComponent
import com.vjh0107.barcode.framework.koin.bean.BarcodeBeanModuleFactory
import com.vjh0107.barcode.framework.koin.injector.ReflectionInjector
import com.vjh0107.barcode.framework.utils.print
import kotlinx.serialization.json.*
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarInputStream
import kotlin.reflect.KClass

abstract class AbstractVelocityComponentHandler<P : AbstractBarcodePlugin, T : IBarcodeComponent>(
    val plugin: P
) : AbstractComponentHandler<T>() {
    private fun findTargetPluginJar(): File {
        val pluginPath = Path.of("plugins")
        val pluginJarPath = Files.newDirectoryStream(pluginPath) { path ->
            path.toFile().isFile && path.toString().endsWith(".jar")
        }.first { path -> plugin::class.qualifiedName == getPluginMainClass(path) }
        return pluginJarPath.toFile()
    }

    private fun getPluginMainClass(source: Path): String {
        JarInputStream(BufferedInputStream(Files.newInputStream(source))).use { `in` ->
            var entry: JarEntry
            while (`in`.nextJarEntry.also { entry = it } != null) {
                if (entry.name == "velocity-plugin.json") {
                    InputStreamReader(`in`, StandardCharsets.UTF_8).use { pluginInfoReader ->
                        val json: Map<String, JsonElement> = Json.parseToJsonElement(pluginInfoReader.readText()).jsonObject
                        return json["main"]!!.jsonPrimitive.content
                    }
                }
            }
            throw NullPointerException("플러그인의 velocity-plugin.json 을 로드할 수 없습니다.")
        }
    }

    final override fun findTargetClasses(): MutableSet<Class<*>> {
        val classes: MutableSet<Class<*>> = mutableSetOf()
        val entries = JarFile(findTargetPluginJar()).entries()
        while (entries.hasMoreElements()) {
            val name = entries.nextElement().name.replace("/", ".")
            if (name.startsWith(getRootPackage()) && name.endsWith(".class")) {
                try {
                    val clazz = Class.forName(name.removeSuffix(".class"))
                    classes.add(clazz)
                } catch (exception: ClassNotFoundException) {
                    exception.printStackTrace()
                }
            }
        }
        return classes
    }

    private fun getRootPackage(): String {
        return plugin::class.java.packageName
    }

    override fun createInstance(clazz: KClass<T>): T {
        val provideInstance: Map<KClass<*>, *> = mapOf(Pair(plugin::class, plugin))
        val instance = ReflectionInjector.createInstance(clazz, provideInstance)
        BarcodeBeanModuleFactory.tryCreateBeanModule(clazz, instance)
        return instance
    }
}
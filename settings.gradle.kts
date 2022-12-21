rootProject.name = "BarcodeFramework"

pluginManagement {
    val kotlinVersion: String by settings
    val ktorVersion: String by settings
    val kspVersion: String by settings
    val barcodeGradlePluginsVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
        id("com.google.devtools.ksp") version kspVersion apply false
        id("com.github.johnrengelman.shadow") version "7.1.2" apply false
        id("com.vjh0107.bukkit-resource-generator") version barcodeGradlePluginsVersion apply false
        id("com.vjh0107.special-source") version barcodeGradlePluginsVersion apply false
        id("com.vjh0107.ksp-extension") version "1.0.2" apply false
        id("com.vjh0107.bukkit-executor") version barcodeGradlePluginsVersion apply false
    }

    repositories {
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

file(rootProject.projectDir.path + "/publish.gradle.kts").let {
    if (it.exists()) {
        apply(it.path)
    }
}

fun includeAll(modulesDir: String) {
    file("${rootProject.projectDir.path}/${modulesDir.replace(":", "/")}/").listFiles()?.forEach { modulePath ->
        include("${modulesDir.replace("/", ":")}:${modulePath.name}")
    }
}


includeBuild("plugins/project-manager")
includeBuild("plugins/module-publisher")
includeBuild("build-logic")

includeAll("modules")
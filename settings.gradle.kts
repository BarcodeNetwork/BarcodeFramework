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
    }

    repositories {
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

includeBuild("plugins/project-manager")
includeBuild("plugins/module-publisher")
includeBuild("build-logic")
include("modules:common")
include("modules:koin")
include("modules:platform-bukkit-common")
include("modules:database")
include("modules:google-sheets")
include("modules:platform-bukkit-v1_19_R1")
include("modules:platform-ktor")
include("modules:platform-bukkit")
//fun getAllProjects(path: String) {
//    project(path).children.forEach {
//        println(it.projectDir)
//        println(it.parent?.name)
//    }
//}
//
//getAllProjects(":modules")

//project(":modules").children.forEach { childProject ->
//    childProject.name = "${rootProject.name.toLowerCase()}-${childProject.name}"
//}
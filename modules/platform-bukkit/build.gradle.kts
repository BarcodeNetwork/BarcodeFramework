plugins {
    id("barcodeframework.shared")
    id("barcodeframework.shadow")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.bukkit-resource-generator")
    id("com.vjh0107.special-source")
    id("com.vjh0107.ksp-extension")
    id("com.vjh0107.bukkit-executor")
    kotlin("plugin.serialization")
}

val isExecutingBukkit = false

tasks.shadowJar {
    this.relocate("dev.jorel.commandapi", "com.vjh0107.barcode.commandapi")
    if (isExecutingBukkit) {
        this.setBuildOutputDir("../../test-bukkit/plugins")
    }
}

barcodeTasks {
    archiveTask = tasks.shadowJar

    bukkitResource {
        main = "com.vjh0107.barcode.framework.BarcodeFrameworkPlugin"
        name = "BarcodeFramework"
        apiVersion = "1.19"
        author = "vjh0107"
        softDepend = listOf(
            "Vault",
            "HolographicDisplays",
            "PlaceholderAPI"
        )
    }
    specialSource {
        version.set("1.19.2")
        archiveTask.set(tasks.shadowJar)
        enabled.set(true)
    }
    bukkitExecutor {
        enabled.set(isExecutingBukkit)
        archiveTask.set(tasks.shadowJar)
        bukkitDir.set(file("../../test-bukkit/"))
        bukkitFileName.set("paper.jar")
    }
}
tasks.shadowJar {
    finalizedBy(tasks.runBukkit)
}
dependencies {
    compileOnly(Deps.Minecraft.PAPER_API)
    compileOnly(Deps.Minecraft.SPIGOT_REMAPPED)
    compileOnly(Deps.Library.NETTY)
    compileOnly(Deps.Minecraft.AUTH_LIB)
    compileOnly(Deps.Minecraft.Plugin.VAULT)
    compileOnly(Deps.Minecraft.Plugin.PAPI)

    api(Deps.Minecraft.Plugin.COMMAND_API)
    api(Deps.KotlinX.Coroutines.CORE)
    api(Deps.KotlinX.Serialization.JSON)
    apiAll(Deps.Ktor.CLIENT)
    apiAll(Deps.EXPOSED)
    api(Deps.Koin.CORE)
    api(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)
    api(Deps.Library.KOTLIN_REFLECT)
    api(Deps.Library.MYSQL_CONNECTOR)
    api(Deps.Library.HIKARICP)
    api(Deps.Library.SQLITE)

    apiModule(Modules.KOIN)
    apiModule(Modules.DATABASE)
    apiModule(Modules.COMMON)
    apiModule(Modules.SHEETS)
    apiModule(Modules.Bukkit.COMMON)
    apiModule(Modules.Bukkit.V1_19_R1)

    testImplementation(Deps.Minecraft.PAPER_API)
    testImplementation(Deps.KotlinX.Coroutines.CORE)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementation(Deps.Library.MOCKK)
    testImplementationAll(Deps.KOTEST)
    testImplementation(Deps.Koin.TEST)
    testImplementationModule(Modules.COMMON)
}
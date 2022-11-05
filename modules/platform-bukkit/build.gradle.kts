plugins {
    id("barcodeframework.shared")
    id("barcodeframework.shadow")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.bukkit-resource-generator")
    id("com.vjh0107.special-source")
    id("com.vjh0107.ksp-extension")
    kotlin("plugin.serialization")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

tasks.shadowJar {
    this.relocate("dev.jorel.commandapi", "com.vjh0107.barcode.commandapi")
}

barcodeTasks {
    archiveTask = tasks.shadowJar

    bukkitResource {
        main = "com.vjh0107.barcode.framework.BarcodeFrameworkPlugin"
        name = "BarcodeFramework"
        apiVersion = "1.18"
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
}

dependencies {
    compileOnly(Deps.Minecraft.PAPER_API)
    compileOnly(Deps.Minecraft.SPIGOT_REMAPPED)
    compileOnly(Deps.Library.NETTY)
    compileOnly(Deps.Minecraft.AUTH_LIB)
    implementation(Deps.Minecraft.Plugin.COMMAND_API)
    implementation(Deps.Minecraft.Plugin.VAULT)
    implementation(Deps.Minecraft.Plugin.PAPI)
    implementation(Deps.KotlinX.Coroutines.CORE)
    implementation(Deps.KotlinX.Serialization.JSON)
    implementationAll(Deps.Ktor.CLIENT)
    implementationAll(Deps.EXPOSED)
    implementation(Deps.Koin.CORE)
    implementation(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)
    implementation(Deps.Library.KOTLIN_REFLECT)
    implementation(Deps.Library.MYSQL_CONNECTOR)
    implementation(Deps.Library.HIKARICP)
    implementation(Deps.Library.SQLITE)

    implementationModule(Modules.KOIN)
    implementationModule(Modules.DATABASE)
    implementationModule(Modules.COMMON)
    implementationModule(Modules.SHEETS)
    implementationModule(Modules.Bukkit.COMMON)
    implementationModule(Modules.Bukkit.V1_19_R1)

    testImplementation(Deps.Minecraft.PAPER_API)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementation(Deps.Library.MOCKK)
    testImplementationAll(Deps.KOTEST)
    testImplementation(Deps.Koin.TEST)
    testImplementationModule(Modules.COMMON)
}
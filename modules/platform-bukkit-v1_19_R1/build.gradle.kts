plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.special-source")
}

barcodeTasks {
    archiveTask = tasks.shadowJar

    specialSource {
        version.set("1.19.2")
        archiveTask.set(tasks.shadowJar)
        enabled.set(true)
    }
}

dependencies {
    compileOnly(Deps.Minecraft.SPIGOT_REMAPPED)
    compileOnly(Deps.Minecraft.PAPER_API)
    compileOnly(Deps.Minecraft.KyoriAdventure.API)
    compileOnly(Deps.Minecraft.KyoriAdventure.BUKKIT)
    apiModule(Modules.COMMON)
    apiModule(Modules.Bukkit.COMMON)
    testImplementationAll(Deps.KOTEST)
    testImplementationModule(Modules.COMMON)
}

plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
}

dependencies {
    compileOnly(Deps.Minecraft.KyoriAdventure.API)
    compileOnly(Deps.Minecraft.SPIGOT_REMAPPED) {
        this.exclude("com.google.gson")
    }
    apiModule(Modules.COMMON)
    apiModule(Modules.KOIN)
    api(Deps.KotlinX.Serialization.JSON)
    api(Deps.Koin.CORE)
    api(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)

    testImplementation(Deps.Minecraft.SPIGOT_REMAPPED)
    testImplementationAll(Deps.KOTEST)
    testImplementationModule(Modules.COMMON)
}

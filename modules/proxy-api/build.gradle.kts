plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
    kotlin("plugin.serialization")
}

dependencies {
    apiModule(Modules.COMMON)
    apiModule(Modules.NETTY)
    apiModule(Modules.KOIN)
    apiAll(Deps.Koin)
    ksp(Deps.Koin.KSP_COMPILER)
    api(Deps.KotlinX.Serialization.JSON)
}
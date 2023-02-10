plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
    kotlin("plugin.serialization")
}

dependencies {
    api(Deps.KotlinX.Coroutines.CORE)
    api(Deps.KotlinX.Serialization.JSON)
    api(Deps.Library.HIKARICP)
    api(Deps.Koin.CORE)
    api(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)
    apiModule(Modules.COMMON)
    apiModule(Modules.KOIN)
    apiAll(Deps.EXPOSED)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
    testImplementation(Deps.Library.MYSQL_CONNECTOR)
}
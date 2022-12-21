plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    kotlin("plugin.serialization")
}

dependencies {
    api(Deps.KotlinX.Coroutines.CORE)
    api(Deps.KotlinX.Serialization.JSON)
    api(Deps.Library.HIKARICP)
    apiModule(Modules.COMMON)
    apiAll(Deps.EXPOSED)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
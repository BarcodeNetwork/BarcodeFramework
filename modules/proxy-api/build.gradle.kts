plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    kotlin("plugin.serialization")
}

dependencies {
    apiModule(Modules.COMMON)
    apiModule(Modules.NETTY)
    api(Deps.KotlinX.Serialization.JSON)
}
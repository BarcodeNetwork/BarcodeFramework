plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
}

dependencies {
    api(Deps.KotlinX.Serialization.JSON)
    api(Deps.Library.KOTLIN_REFLECT)
    testImplementationAll(Deps.KOTEST)
}
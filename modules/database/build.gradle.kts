plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    kotlin("plugin.serialization")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    compileOnly(Deps.KotlinX.Coroutines.CORE)
    compileOnly(Deps.KotlinX.Serialization.JSON)
    compileOnly(Deps.Library.HIKARICP)
    compileOnlyModule(Modules.COMMON)
    compileOnlyAll(Deps.EXPOSED)

    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
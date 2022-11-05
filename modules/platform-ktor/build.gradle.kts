plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
    kotlin("plugin.serialization")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    implementationModule(Modules.COMMON)
    with(Deps.Koin) {
        implementation(CORE)
        implementation(ANNOTATIONS)
        ksp(KSP_COMPILER)
        testImplementation(TEST)
    }
    implementation(Deps.KotlinX.Coroutines.CORE)
    implementationModule(Modules.KOIN)
    implementationModule(Modules.DATABASE)
    implementation(Deps.Library.HIKARICP)

    implementation(Deps.Library.Logger.LOGBACK_CLASSIC)
    implementation(Deps.Library.Logger.SLF4J_JDK14)
    implementationAll(Deps.Ktor.SERVER)
    implementation(Deps.KotlinX.Serialization.JSON)

    implementation(Deps.Library.MYSQL_CONNECTOR)
    implementationAll(Deps.EXPOSED)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
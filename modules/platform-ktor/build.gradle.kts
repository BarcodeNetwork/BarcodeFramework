plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
    kotlin("plugin.serialization")
}

dependencies {
    apiModule(Modules.COMMON)
    with(Deps.Koin) {
        api(CORE)
        api(ANNOTATIONS)
        ksp(KSP_COMPILER)
        testImplementation(TEST)
    }
    api(Deps.KotlinX.Coroutines.CORE)
    apiModule(Modules.KOIN)
    apiModule(Modules.DATABASE)
    api(Deps.Library.HIKARICP)
    api(Deps.Library.Logger.LOGBACK_CLASSIC)
    api(Deps.Library.Logger.SLF4J_JDK14)
    apiAll(Deps.Ktor.SERVER)
    api(Deps.KotlinX.Serialization.JSON)
    api(Deps.Library.MYSQL_CONNECTOR)
    apiAll(Deps.EXPOSED)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
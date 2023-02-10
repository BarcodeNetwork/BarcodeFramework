plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
}

dependencies {
    api(Deps.KotlinX.Coroutines.CORE)
    apiModule(Modules.COMMON)
    api(Deps.Koin.CORE)
    api(Deps.Koin.ANNOTATIONS)
    api(Deps.Kotlin.KOTLIN_REFLECT)

    testImplementation(Deps.Koin.TEST)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
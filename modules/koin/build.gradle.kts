plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    implementation(Deps.KotlinX.Coroutines.CORE)
    implementationModule(Modules.COMMON)
    implementation(Deps.Koin.CORE)
    implementation(Deps.Koin.ANNOTATIONS)
    implementation(Deps.Library.KOTLIN_REFLECT)

    testImplementation(Deps.Koin.TEST)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
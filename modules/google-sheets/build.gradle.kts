plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("com.vjh0107.ksp-extension")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    compileOnly(Deps.KotlinX.Coroutines.CORE)
    compileOnlyModule(Modules.COMMON)
    implementation(Deps.Koin.CORE)
    implementation(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)
    implementationAll(Deps.GOOGLE_SHEETS)

    testImplementation(Deps.Koin.TEST)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}
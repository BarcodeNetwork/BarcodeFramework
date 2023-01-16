plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
    id("barcodeframework.shadow")
    id("com.vjh0107.ksp-extension")
    kotlin("kapt")
}

dependencies {
    compileOnly(Deps.Minecraft.VELOCITY)
    kapt(Deps.Minecraft.VELOCITY)
    compileOnly(Deps.Library.NETTY)

    api(Deps.KotlinX.Coroutines.CORE)
    api(Deps.Koin.CORE)
    api(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)
    api(Deps.Library.KOTLIN_REFLECT)

    apiModule(Modules.COMMON)
    apiModule(Modules.KOIN)
    apiModule(Modules.NETTY)
    apiModule(Modules.PROXY_API)

    testImplementation(Deps.Koin.TEST)
    testImplementation(Deps.KotlinX.Coroutines.TEST)
    testImplementationAll(Deps.KOTEST)
}

tasks.build {
    dependsOn(tasks.shadowJar.get())
}
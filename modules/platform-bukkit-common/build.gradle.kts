plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")

    id("com.vjh0107.ksp-extension")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    compileOnly(Deps.Minecraft.SPIGOT_REMAPPED) {
        this.exclude("com.google.gson")
    }
    implementationModule(Modules.COMMON)
    implementationModule(Modules.KOIN)
    compileOnly(Deps.Minecraft.KyoriAdventure.API)
    implementation(Deps.KotlinX.Serialization.JSON)
    compileOnly(Deps.Koin.CORE)
    compileOnly(Deps.Koin.ANNOTATIONS)
    ksp(Deps.Koin.KSP_COMPILER)

    testImplementation(Deps.Minecraft.SPIGOT_REMAPPED)
    testImplementationAll(Deps.KOTEST)
    testImplementationModule(Modules.COMMON)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

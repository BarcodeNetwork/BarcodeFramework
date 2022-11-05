plugins {
    id("barcodeframework.shared")
    id("barcodeframework.module-publisher")
}

group = "com.vjh0107.barcode"
version = "1.0.0"

dependencies {
    implementation(Deps.KotlinX.Serialization.JSON)
    implementation(Deps.Library.KOTLIN_REFLECT)
    testImplementationAll(Deps.KOTEST)
}
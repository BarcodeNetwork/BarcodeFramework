plugins {
    id("barcodeframework.project-manager")
}

group = "com.vjh0107.barcode"
version = "1.0.1"

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}
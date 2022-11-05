plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    api(gradleApi())
}

gradlePlugin {
    plugins {
        register("barcodeframework-module-publisher") {
            id = "barcodeframework.module-publisher"
            implementationClass = "com.vjh0107.barcode.framework.modulepublisher.ModulePublisherPlugin"
        }
    }
}
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.BarcodeNetwork.BarcodeGradlePlugins:project-extensions:1.1.1")
}

gradlePlugin {
    plugins {
        register("barcodeframework-project-manager") {
            id = "barcodeframework.project-manager"
            implementationClass = "com.vjh0107.barcode.framework.projectmanager.ProjectManagerPlugin"
        }
    }
}
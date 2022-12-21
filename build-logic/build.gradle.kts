import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    `kotlin-dsl`
}

val properties = loadProperties(rootProject.gradle.parent?.rootProject?.projectDir?.path + "/gradle.properties")
val kotlinVersion = properties.getProperty("kotlinVersion")
val shadowPluginVersion = properties.getProperty("shadowPluginVersion")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:$shadowPluginVersion")
    implementation("com.github.BarcodeNetwork.BarcodeGradlePlugins:project-extensions:1.1.2")
}
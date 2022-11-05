plugins {
    java
    kotlin("jvm")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
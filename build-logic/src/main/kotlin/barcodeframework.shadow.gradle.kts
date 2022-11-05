import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    java
    id("com.github.johnrengelman.shadow")
}

tasks.shadowJar {
    setBuildOutputDir()
    val localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
    this.archiveBaseName.set("${project.rootProject.name}-${project.name}")
    this.archiveClassifier.set(localDateTime)
}
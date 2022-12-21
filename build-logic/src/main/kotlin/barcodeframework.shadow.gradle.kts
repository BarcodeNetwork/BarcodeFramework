plugins {
    java
    id("com.github.johnrengelman.shadow")
}

tasks.shadowJar {
    setBuildOutputDir()
    this.archiveBaseName.set("${project.rootProject.name}-${project.name}")
}

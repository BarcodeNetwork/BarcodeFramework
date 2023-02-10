import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    java
    kotlin("jvm")
}

val projectGroup: String by project
group = projectGroup
val projectVersion: String by project
version = projectVersion

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

extra["humanReadableNow"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
package com.vjh0107.barcode.framework.modulepublisher

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

/**
 * MavenCentral 에 publish 해주는 플러그인 입니다.
 */
class ModulePublisherPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            init(this)
            val javadocJar by tasks.registering(Jar::class) {
                archiveClassifier.set("javadoc")
            }
            val publishConfig: PublishingExtension.() -> Unit = {
                repositories {
                    maven {
                        name = "sonatype"
                        setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = getExtra("ossrhUsername")
                            password = getExtra("ossrhPassword")
                        }
                    }
                }
                publications {
                    create<MavenPublication>("maven") {
                        artifact(javadocJar.get())
                        pom {
                            name.set(getExtra("publish.name"))
                            description.set(getExtra("publish.description"))
                            url.set(getExtra("publish.url"))

                            licenses {
                                license {
                                    name.set(getExtra("publish.license.name"))
                                    url.set(getExtra("publish.license.url"))
                                }
                            }
                            developers {
                                developer {
                                    id.set(getExtra("publish.developer.id"))
                                    name.set(getExtra("publish.developer.name"))
                                    email.set(getExtra("publish.developer.email"))
                                }
                            }
                            scm {
                                url.set(getExtra("publish.scm.url"))
                            }
                        }

                        artifactId = "${target.rootProject.name.toId()}-${target.project.name.toId()}"
                    }
                }
            }
            val signingConfig: SigningExtension.() -> Unit = {
                val publications = extensions.getByType(PublishingExtension::class).publications
                sign(publications)
            }

            extensions.configure<PublishingExtension>("publishing", publishConfig)
            extensions.configure("signing", signingConfig)
        }
    }



    private fun init(target: Project) {
        with(target) {
            if (!plugins.hasPlugin(JavaPlugin::class.java)) {
                plugins.apply(JavaPlugin::class.java)
            }
            if (!plugins.hasPlugin(MavenPublishPlugin::class.java)) {
                plugins.apply(MavenPublishPlugin::class.java)
            }
            if (!plugins.hasPlugin(SigningPlugin::class.java)) {
                plugins.apply(SigningPlugin::class.java)
            }

            val secretProps = gradle.rootProject.file("publish.gradle.kts")
            if (secretProps.exists()) {
                apply(secretProps)
            }
        }
    }

    private fun String.toId(): String {
        return this
            .toLowerCase()
            .replace("_", "-")
            .replace(" ", "-")
    }
}
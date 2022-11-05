package com.vjh0107.barcode.framework.modulepublisher

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

class ModulePublisherPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        initPlugins(target)
        val publishConfig: PublishingExtension.() -> Unit = {
            publications {
                create<MavenPublication>("maven") {
                    groupId = target.group.toString()
                    artifactId = "${target.rootProject.name.toId()}-${target.project.name.toId()}"
                    version = target.version.toString()
                    from(target.components["java"])
                }
            }
        }
        target.extensions.configure<PublishingExtension>("publishing", publishConfig)
    }

    private fun initPlugins(target: Project) {
        if (!target.plugins.hasPlugin(MavenPublishPlugin::class.java)) {
            target.plugins.apply(MavenPublishPlugin::class.java)
        }
        if (!target.plugins.hasPlugin(JavaPlugin::class.java)) {
            target.plugins.apply(JavaPlugin::class.java)
        }
    }

    private fun String.toId(): String {
        return this
            .toLowerCase()
            .replace("_", "-")
            .replace(" ", "-")
    }
}
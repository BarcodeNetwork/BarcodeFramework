package com.vjh0107.barcode.framework.projectmanager

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectManagerPlugin : Plugin<Project> {
    companion object {
        lateinit var project: Project
    }
    override fun apply(target: Project) {
        project = target
    }
}
package com.sildeag.sentinel.architecture

import org.gradle.api.Plugin
import org.gradle.api.Project
class SentinelPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("sentinelScan", SentinelScanTask::class.java) {
            it.group = "verification"
            it.description = "Runs the Architecture Sentinel scanners"
        }
    }
}
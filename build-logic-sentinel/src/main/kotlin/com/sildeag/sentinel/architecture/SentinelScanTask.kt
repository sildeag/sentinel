package com.sildeag.sentinel.architecture

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
abstract class SentinelScanTask : DefaultTask() {
    @TaskAction
    fun runScan() {
        val root = project.rootDir
        println("=== Architecture Sentinel Plugin ===")
        println("Scanning project: ${root.absolutePath}\n")
        // Note: ArchitectureSentinel needs to be available on the classpath.
        // If it's in the :architecture module, we need to ensure this task can access it.
        // For now, we'll keep the reference as is.
        ArchitectureSentinel.run(root, ArchitectureSentinel.ScanMode.ALL)
        println("\n=== Sentinel scan complete ===")
    }
}
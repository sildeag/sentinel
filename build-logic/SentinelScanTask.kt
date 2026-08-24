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
        ArchitectureSentinel.run(root, ArchitectureSentinel.ScanMode.ALL)
        println("\n=== Sentinel scan complete ===")
    }
}

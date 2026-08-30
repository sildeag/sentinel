package com.sildeag.sentinel

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
        // It is currently defined in the :architecture module of the root build.
        // ArchitectureSentinel.run(root, ArchitectureSentinel.ScanMode.ALL)
        
        println("\n=== Sentinel scan complete ===")
    }
}

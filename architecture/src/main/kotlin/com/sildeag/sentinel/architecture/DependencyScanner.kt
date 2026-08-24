package com.sildeag.sentinel.architecture

import java.io.File

object DependencyScanner {
    fun scan(root: File) {
        root.walkTopDown()
            .filter { it.name.startsWith("build.gradle") }
            .forEach { file ->
                val fromModule = ModuleDetector.detect(file.path)
                file.readLines().forEach { line ->
                    val match =
                        DependencyPatterns.projectRef.find(line)
                    if (match != null) {
                        val toModule =
                            ModuleDetector.detect(match.groupValues[1])
                        DependencyRules.isForbidden(fromModule,
                            toModule)?.let { reason ->
                            WarningPrinter.print(
                                Warning(fromModule, file.path,
                                    line.trim(), reason)
                            )
                        }
                    }
                }
            }
    }
}

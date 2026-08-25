package com.sildeag.sentinel.architecture

import java.io.File

object UIScanner {
    fun scan(root: File) {
        root.walkTopDown()
            .filter { it.extension == "kt" }
            .forEach { file ->
                val module = ModuleDetector.detect(file.path)
                file.readLines().forEach { line ->
                    UIRules.isForbidden(module, line)?.let { reason
                        ->
                        WarningPrinter.print(
                            Warning(module, file.path, line.trim(),
                                reason)
                        )
                    }
                }
            }
    }
}

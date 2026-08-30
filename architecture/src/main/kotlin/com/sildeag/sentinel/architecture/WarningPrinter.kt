package com.sildeag.sentinel.architecture

object WarningPrinter {
    fun print(result: FileAnalysisResult) {
        println("Module: ${result.module}")
        result.importViolations.forEach { v ->
            println("DEBUG RAW: $v")
            println("DEBUG MODULE: ${v.module}")
            println("DEBUG FILE: ${v.ktmodule}")
            println("DEBUG IMPORT: ${v.import}")
            println("DEBUG MESSAGE: ${v.message}")
            println("DEBUG SUGGESTION: ${v.suggestion}")
            println("DEBUG ID: ${v.id}")
        }


        result.importViolations.forEach { v ->
            println(v)
        }

        result.dependencyViolations.forEach { v ->
            println("Dependency violation: ${v.from} -> ${v.to}")
            println(" ${v.message}")
        }
    }
}

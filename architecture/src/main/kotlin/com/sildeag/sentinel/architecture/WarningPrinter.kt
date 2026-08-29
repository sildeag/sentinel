package com.sildeag.sentinel.architecture

object WarningPrinter {
    fun print(result: FileAnalysisResult) {
        println("Module: ${result.module}")
        result.importViolations.forEach { v ->
            println(" Import violation: ${v.import}")
            println(" ${v.reason}")
        }
        result.dependencyViolations.forEach { v ->
            println(" Dependency violation: ${v.from} -> ${v.to}")
            println(" ${v.message}")
        }
    }
}
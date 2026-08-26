package com.sildeag.sentinel.architecture

import java.io.File
class ArchitectureSentinel(
    private val projectRoot: File
) {
    fun run() {
        println("Sentinel: scanning project at ${projectRoot.absolutePath}")
            val results = mutableListOf<FileAnalysisResult>()
        projectRoot.walkTopDown().forEach { file ->
            if (!file.isFile || !file.name.endsWith(".kt"))
                return@forEach
            val modulePath = modulePathFor(file)
            val module = classifyModule(modulePath)
            val imports = extractImports(file)
            val dependencies = extractDependencies(modulePath)
            val result = ArchitectureRules.analyzeFile(
                modulePath = modulePath,
                imports = imports,
                dependencies = dependencies
            )
            if (result.importViolations.isNotEmpty() ||
                result.dependencyViolations.isNotEmpty()
            ) {
                results += result
            }
        }
        report(results)
    }
    private fun modulePathFor(file: File): String {
        val path =
            file.absolutePath.replace(projectRoot.absolutePath, "")
        val parts = path.split(File.separator)
        return if (parts.size > 2) ":${parts[1]}" else ":unknown"
    }
    private fun extractImports(file: File): List<String> =
        file.readLines()
            .filter { it.trim().startsWith("import ") }
            .map { it.removePrefix("import ").trim() }
    private fun extractDependencies(modulePath: String): List<String>
    {
        // You can enhance this later by reading build.gradle.kts
        // For now, Sentinel only checks project dependencies declared in settings.gradle
        return emptyList()
    }
    private fun report(results: List<FileAnalysisResult>) {
        if (results.isEmpty()) {
            println("Sentinel: no violations found.")
            return
        }
        println("Sentinel: violations found (${results.size} modules):")
        results.forEach { result ->
            println("\nModule: ${result.module}")
            result.importViolations.forEach { v ->
                println(" Import violation: ${v.import}")
                println(" ${v.message}")
                v.suggestion?.let { println(" Suggestion: $it") }
            }
            result.dependencyViolations.forEach { v -> println(" Dependency violation: ${v.from} -> ${v.to}")
                        println(" ${v.message}")
            }
        }
    }
}

/*
import java.io.File
object ArchitectureSentinel {
    enum class ScanMode { IMPORTS, UI, PLATFORM, THEME, DI, DEPENDENCY, ALL }
    fun run(root: File, mode: ScanMode = ScanMode.ALL) {
        println("Running Sentinel mode: $mode")
        when (mode) {
            ScanMode.IMPORTS -> ImportScanner.scan(root)
            ScanMode.UI -> UIScanner.scan(root)
            ScanMode.PLATFORM -> PlatformScanner.scan(root)
            ScanMode.THEME -> ThemeScanner.scan(root)
            ScanMode.DI -> DIScanner.scan(root)
            ScanMode.DEPENDENCY -> DependencyScanner.scan(root)
            ScanMode.ALL -> {
                ImportScanner.scan(root)
                UIScanner.scan(root)
                PlatformScanner.scan(root)
                ThemeScanner.scan(root)
                DIScanner.scan(root)
                DependencyScanner.scan(root)
            }
        }
    }
}
*/
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
            val module = classifyEnumModule(modulePath)
            val imports = extractImports(file)
            val uiPatterns = extractUIPatterns(file)
            val themePatterns = extractThemePatterns(file)
            val platformPatterns = extractPlatformPatterns(file)
            val diPatterns = extractDIPatterns(file)
            val dependencies = extractDependencies(modulePath)
            val result = ArchitectureRules.analyzeFile(
                modulePath = modulePath,
                imports = imports,
                uiPatterns = uiPatterns,
                themePatterns = themePatterns,
                platformPatterns = platformPatterns,
                diPatterns = diPatterns,
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
    private fun extractUIPatterns(file: File): List<String> =
        file.readLines().filter { UIPatterns.isUIFunction(it) ||
                UIPatterns.isComposable(it) ||
                UIPatterns.isPreview(it) }
    private fun extractThemePatterns(file: File): List<String> =
        file.readLines().filter { ThemePatterns.typography.containsMatchIn(it) ||
            ThemePatterns.textStyle.containsMatchIn(it) ||
            ThemePatterns.fontWeight.containsMatchIn(it) ||
            ThemePatterns.sp.containsMatchIn(it) }
    private fun extractPlatformPatterns(file: File): List<String> =
        file.readLines().filter { PlatformPatterns.android.containsMatchIn(it) ||

            PlatformPatterns.desktopAwt.containsMatchIn(it) ||
            PlatformPatterns.desktopSwing.containsMatchIn(it) }
    private fun extractDIPatterns(file: File): List<String> =
        file.readLines().filter { DIPatterns.isDIImport(it) ||
                DIPatterns.isDIConstruct(it) ||
                DIPatterns.isRepositoryImpl(it) ||
                DIPatterns.isDIModuleBlock(it) }
    private fun extractDependencies(modulePath: String): List<String>
    {
        // Later: parse settings.gradle or build.gradle.kts
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
            result.dependencyViolations.forEach { v ->
                println(" Dependency violation: ${v.from} -> ${v.to}")
                        println(" ${v.message}")
            }
        }
    }
}
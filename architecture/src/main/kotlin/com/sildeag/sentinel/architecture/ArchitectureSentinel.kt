package com.sildeag.sentinel.architecture

import java.io.File

class ArchitectureSentinel(
    private val projectRoot: File
) {
    // NEW: Kotlin 2.4.0 Explicit Backing Field
    // Outside this class, 'results' is a read-only List.
    // Inside this class, 'results' is automatically seen as MutableList.
    val results: List<FileAnalysisResult>
        field = mutableListOf<FileAnalysisResult>()

    fun run() {
        println("Sentinel: scanning project at ${projectRoot.absolutePath}")
        
        results.clear() 

        projectRoot.walkTopDown().forEach { file ->
            // NEW: Kotlin 2.4.0 Context Parameter usage
            with(file) {
                if (!isKotlinSource()) return@forEach
            }

            val ktmodule = file.relativeTo(projectRoot).path

            val modulePath = modulePathFor(file)
            //println("Filename $ktmodule, module $modulePath,file.name ${file.name}\n")
            val imports = extractImports(file)
            val uiPatterns = extractUIPatterns(file)
            val themePatterns = extractThemePatterns(file)
            val platformPatterns = extractPlatformPatterns(file)
            val diPatterns = extractDIPatterns(file)
            val dependencies = extractDependencies(modulePath)
            
            val result = ArchitectureRules.analyzeFile(
                modulePath = modulePath,
                ktmodule = ktmodule,
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
                results.add(result) 
            }
        }
        report(results)
    }

    // NEW: Kotlin 2.4.0 Context Parameter
    // This function requires a 'File' context to be present at the call site.
    context(file: File)
    private fun isKotlinSource(): Boolean = file.isFile && file.name.endsWith(".kt")

    private fun modulePathFor(file: File): String {
        val path = file.absolutePath.replace(projectRoot.absolutePath, "")
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

    private fun extractDependencies(modulePath: String): List<String> = emptyList()

    private fun report(results: List<FileAnalysisResult>) {
        if (results.isEmpty()) {
            println("Sentinel: no violations found.")
            return
        }

        println("Sentinel: violations found (${results.size} files):")

        // Flatten all violations and group by file path
        val grouped = results
            .flatMap { it.importViolations }
            .groupBy { it.ktmodule }

        grouped.forEach { (file, violations) ->
            if (violations.isEmpty()) return@forEach

            val module = violations.first().module

            println("\nModule: $module")
            println("File: $file")
            println("Violations:")

            violations
                .map { v -> v.copy(import = v.import.removePrefix("import ").trim()) }
                .distinctBy { it.import }  // remove duplicates
                .forEach { v ->
                    println(" - Import: ${v.import}")
                    println("   Message: ${v.message}")
                    v.suggestion?.let { println("   Suggestion: $it") }
                }
        }
    }




}

/*
// --- OLD WAY (Pre Kotlin 2.4.0) ---

class ArchitectureSentinel(private val projectRoot: File) {
    
    // 1. Backing Property (No Explicit Backing Field)
    private val _results = mutableListOf<FileAnalysisResult>()
    val results: List<FileAnalysisResult> get() = _results

    fun run() {
        projectRoot.walkTopDown().forEach { file ->
            // 2. Normal Extension or Member Function (No Context Parameters)
            if (!isKotlinSource(file)) return@forEach
            // ...
        }
    }

    private fun isKotlinSource(file: File): Boolean = 
        file.isFile && file.name.endsWith(".kt")
}
*/

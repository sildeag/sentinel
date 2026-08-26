package com.sildeag.sentinel.architecture

data class FileAnalysisResult(
    val module: Module,
    val importViolations: List<ImportViolation>,
    val dependencyViolations: List<DependencyViolation>
)
object ArchitectureRules {
    fun analyzeFile(
        modulePath: String,
        imports: List<String>,
        dependencies: List<String> // Gradle project paths this module depends on
    ): FileAnalysisResult {
        val module = classifyModule(modulePath)
        val importViolations = imports.mapNotNull()
        { ImportRules.checkImport(module, it) }
        val dependencyViolations = dependencies.mapNotNull { depPath
            ->
            val toModule = classifyModule(depPath)
            DependencyRules.checkDependency(module, toModule)
        }
        return FileAnalysisResult(
            module = module,
            importViolations = importViolations,
            dependencyViolations = dependencyViolations
        )
    }
}
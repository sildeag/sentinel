package com.sildeag.sentinel.architecture
data class FileAnalysisResult(
    val module: EnumModule,
    val importViolations: List<ImportViolation>,
    val dependencyViolations: List<DependencyViolation>
)
object ArchitectureRules {
    fun analyzeFile(
        modulePath: String,
        imports: List<String>,
        uiPatterns: List<String>,
        themePatterns: List<String>,
        platformPatterns: List<String>,
        diPatterns: List<String>,
        dependencies: List<String>
    ): FileAnalysisResult {
        val module = classifyEnumModule(modulePath)
        val importViolations = buildList {
            addAll(imports.mapNotNull
            { ImportRules.checkImport(module, it) })
            addAll(uiPatterns.mapNotNull { UIRules.checkRules(module,
                it) })
            addAll(themePatterns.mapNotNull
            { ThemeRules.checkRules(module, it) })
            addAll(platformPatterns.mapNotNull
            { PlatformRules.checkRules(module, it) })
            addAll(diPatterns.mapNotNull
            { DIRules.checkImport(module, it) })
        }
        val dependencyViolations = dependencies.mapNotNull { depPath
            ->
            val toModule = classifyEnumModule(depPath)
            DependencyRules.checkDependency(module, toModule)
        }
        return FileAnalysisResult(
            module = module,
            importViolations = importViolations,
            dependencyViolations = dependencyViolations
        )
    }
}


package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid

data class FileAnalysisResult(
    val module: EnumModule,
    val importViolations: List<ImportViolation>,
    val dependencyViolations: List<DependencyViolation>
)

object ArchitectureRules {

    fun analyzeFile(
        modulePath: String,
        ktmodule: String,
        imports: List<String>,
        uiPatterns: List<String>,
        themePatterns: List<String>,
        platformPatterns: List<String>,
        diPatterns: List<String>,
        dependencies: List<String>
    ): FileAnalysisResult {

        val module = classifyEnumModule(modulePath)
        if (module == EnumModule.UNKNOWN) {
            return FileAnalysisResult(
                module = EnumModule.UNKNOWN,
                importViolations = emptyList(),
                dependencyViolations = emptyList()
            )
        }

        val id = Uuid.random()

        val importViolations = buildList {

            addAll(imports.mapNotNull {
                ImportRules.checkImport(module, ktmodule, id, it)
            })

            addAll(uiPatterns.mapNotNull {
                UIRules.checkRules(module, ktmodule, id, it)
            })

            addAll(themePatterns.mapNotNull {
                ThemeRules.checkRules(module, ktmodule, id, it)
            })

            addAll(platformPatterns.mapNotNull {
                PlatformRules.checkRules(module, ktmodule, id, it)
            })

            addAll(diPatterns.mapNotNull {
                DIRules.checkRules(module, ktmodule, id, it)
            })
        }.filter { violation ->
            violation.message.isNotBlank() && violation.import.isNotBlank()
        }


        val dependencyViolations = dependencies.mapNotNull { depPath ->
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
/*
    val importViolations = buildList {
        addAll(imports.mapNotNull { ImportRules.checkImport(module, ktmodule, id, it) })
        addAll(uiPatterns.mapNotNull { UIRules.checkRules(module, ktmodule, id, it) })
        addAll(themePatterns.mapNotNull { ThemeRules.checkRules(module, ktmodule, id, it) })
        addAll(platformPatterns.mapNotNull { PlatformRules.checkRules(module, ktmodule, id, it) })
        addAll(diPatterns.mapNotNull { DIRules.checkRules(module, ktmodule, id, it) })
        }.filter { violation ->
    violation.message.isNotBlank() || violation.import.isNotBlank()
    }

         */
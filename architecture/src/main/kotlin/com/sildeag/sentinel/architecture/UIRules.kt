package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid

object UIRules {
    fun checkRules(module: EnumModule, ktmodule: String, id: Uuid, import: String):
            ImportViolation? {
        val policy = ModulePolicyTable.policy[module]
        // UI code is forbidden in non‑UI modules
        if (policy != ModulePolicy.UI) {
            when {
                UIPatterns.isComposable(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "Composable forbidden in $module",
                        suggestion = "Move composables to a UI module."
                    )
                UIPatterns.isPreview(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "Preview forbidden in $module",
                        suggestion = "Move previews to a UI module."
                    )
                UIPatterns.isUIFunction(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,                        message = "UI function forbidden in $module",
                        suggestion = "Move UI functions to a UI module."
                    )
            }
        }
        return null
    }
}

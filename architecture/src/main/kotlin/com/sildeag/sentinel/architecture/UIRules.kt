package com.sildeag.sentinel.architecture

object UIRules {
    fun isForbidden(module: Module_old, line: String): String? {
        return when (module) {
            Module_old.CORE, Module_old.PDF, Module_old.APP_ANDROID,
            Module_old.APP_DESKTOP -> {
                when {
                    UIPatterns.composable.containsMatchIn(line) ->
                        "Composable forbidden in $module"
                    UIPatterns.preview.containsMatchIn(line) ->
                        "Preview forbidden in $module"
                    UIPatterns.uiFunctions.any { line.contains(it) }
                        -> "UI function forbidden in $module"
                    else -> null
                }
            }
            else -> null
        }
    }
}

package com.sildeag.sentinel.architecture

object UIRules {
    fun isForbidden(module: Module, line: String): String? {
        return when (module) {
            Module.CORE, Module.PDF, Module.APP_ANDROID,
            Module.APP_DESKTOP -> {
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

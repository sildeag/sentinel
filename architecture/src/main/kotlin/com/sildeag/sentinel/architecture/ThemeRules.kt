package com.sildeag.sentinel.architecture
object ThemeRules {
    fun checkRules(module: EnumModule, import: String):
            ImportViolation? {
        val policy = ModulePolicyTable.policy[module]
        // Theme code is only allowed in UI modules
        if (policy != ModulePolicy.UI) {
            fun forbidden(msg: String, suggestion: String? = null) =
                ImportViolation(module, import, msg, suggestion)
            return when {
                ThemePatterns.typography.containsMatchIn(import) ->
                    forbidden(
                        "Typography forbidden in $module.",
                        "Move typography code to a UI theme module."
                    )
                ThemePatterns.textStyle.containsMatchIn(import) ->
                    forbidden(
                        "TextStyle forbidden in $module.",
                        "Move TextStyle code to a UI theme module."
                    )
                ThemePatterns.fontWeight.containsMatchIn(import) ->
                    forbidden(
                        "FontWeight forbidden in $module.",
                        "Move font weight definitions to a UI theme module."
                    )
                ThemePatterns.sp.containsMatchIn(import) ->
                    forbidden(
                        "sp forbidden in $module.",
                        "Move sp usage to a UI theme module."
                    )
                else -> null
            }
        }
        return null
    }
}
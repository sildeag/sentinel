package com.sildeag.sentinel.architecture

object ImportRules {
    fun isForbidden(module: Module, line: String): String? {
        return when (module) {
            Module.CORE -> when {
                ImportPatterns.compose.containsMatchIn(line) ->
                    "Compose imports forbidden in core"
                ImportPatterns.android.containsMatchIn(line) ->
                    "Android imports forbidden in core"
                ImportPatterns.awt.containsMatchIn(line) ->
                    "Desktop imports forbidden in core"
                else -> null
            }
            Module.UI_COMMON -> when {
                ImportPatterns.android.containsMatchIn(line) ->
                    "Android imports forbidden in ui-common"
                ImportPatterns.awt.containsMatchIn(line) ->
                    "Desktop imports forbidden in ui-common"
                else -> null
            }
            else -> null
        }
    }
}
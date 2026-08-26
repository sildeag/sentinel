package com.sildeag.sentinel.architecture

object ThemeRules {
    fun isForbidden(module: Module_old, filePath: String, line: String):
            Any? {
        val isThemeFile = "theme" in filePath
        if (isThemeFile) return null
        return when {
            ThemePatterns.typography.containsMatchIn(line) ->
                "Typography forbidden outside theme"
            ThemePatterns.textStyle.containsMatchIn(line) ->
                "TextStyle forbidden outside theme"
            ThemePatterns.fontWeight.containsMatchIn(line) ->
                "FontWeight forbidden outside theme"
            ThemePatterns.sp.containsMatchIn(line) ->
                "sp forbidden outside theme"
            else -> null
        }
    }
}

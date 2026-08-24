package com.sildeag.sentinel.architecture

object PlatformRules {
    fun isForbidden(module: Module, line: String): String? {
        return when (module) {
            Module.UI_COMMON, Module.CORE, Module.PDF -> when {
                PlatformPatterns.android.containsMatchIn(line) ->
                    "Android code forbidden in $module"
                PlatformPatterns.desktopAwt.containsMatchIn(line) ->
                    "Desktop code forbidden in $module"
                PlatformPatterns.desktopSwing.containsMatchIn(line)
                    -> "Desktop code forbidden in $module"
                else -> null
            }
            Module.ANDROID_UI -> when {
                PlatformPatterns.desktopAwt.containsMatchIn(line) ->
                    "Desktop code forbidden in android-ui"
                else -> null
            }
            Module.DESKTOP_UI -> when {
                PlatformPatterns.android.containsMatchIn(line) ->
                    "Android code forbidden in desktop-ui"
                else -> null
            }
            else -> null
        }
    }
}

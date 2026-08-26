package com.sildeag.sentinel.architecture

object PlatformRules {
    fun isForbidden(module: Module_old, line: String): String? {
        return when (module) {
            Module_old.UI_COMMON, Module_old.CORE, Module_old.PDF -> when {
                PlatformPatterns.android.containsMatchIn(line) ->
                    "Android code forbidden in $module"
                PlatformPatterns.desktopAwt.containsMatchIn(line) ->
                    "Desktop code forbidden in $module"
                PlatformPatterns.desktopSwing.containsMatchIn(line)
                    -> "Desktop code forbidden in $module"
                else -> null
            }
            Module_old.ANDROID_UI -> when {
                PlatformPatterns.desktopAwt.containsMatchIn(line) ->
                    "Desktop code forbidden in android-ui"
                else -> null
            }
            Module_old.DESKTOP_UI -> when {
                PlatformPatterns.android.containsMatchIn(line) ->
                    "Android code forbidden in desktop-ui"
                else -> null
            }
            else -> null
        }
    }
}

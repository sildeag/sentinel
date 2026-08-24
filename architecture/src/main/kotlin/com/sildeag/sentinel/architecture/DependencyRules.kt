package com.sildeag.sentinel.architecture

object DependencyRules {
    fun isForbidden(from: Module, to: Module): String? {
        return when (from) {
            Module.CORE -> if (to != Module.CORE)
                "core must not depend on $to" else null
                Module.PDF -> if (to != Module.CORE)
                    "pdf must depend only on core" else null
                Module.UI_COMMON -> if (to !in listOf(Module.CORE,
                    Module.PDF)) "ui-common must not depend on $to" else null
            Module.ANDROID_UI -> if (to == Module.DESKTOP_UI)
                "android-ui must not depend on desktop-ui" else null
            Module.DESKTOP_UI -> if (to == Module.ANDROID_UI)
                "desktop-ui must not depend on android-ui" else null
            Module.APP_ANDROID -> if (to != Module.ANDROID_UI)
                "appandroid must depend only on android-ui" else null
            Module.APP_DESKTOP -> if (to != Module.DESKTOP_UI)
                "appdesktop must depend only on desktop-ui" else null
            else -> null
        }
    }
}

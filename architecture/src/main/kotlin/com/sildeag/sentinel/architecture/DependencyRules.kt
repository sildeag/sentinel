package com.sildeag.sentinel.architecture
data class DependencyViolation(
    val from: Module,
    val to: Module,
    val message: String
)
object DependencyRules {
    fun checkDependency(from: Module, to: Module):
            DependencyViolation? {
        // test harness: ignore everything
        if (from == Module.TEST_HARNESS || to == Module.TEST_HARNESS)
            return null
        return when (from) {
            Module.CORE,
            Module.PLATFORM,
            Module.SETTINGS,
            Module.STORAGE,
            Module.DI,
            Module.APP_COMMON -> {
                // core-like modules must not depend on any project module
                if (to != Module.UNKNOWN) {
                    DependencyViolation(
                        from,
                        to,
                        "Core-like module $from must not depend on project module $to."
                    )
                } else null
            }
            Module.FEATURE_CAPABILITIES,
            Module.FEATURE_FORM,
            Module.FEATURE_GENEALOGY,
            Module.FEATURE_HISTORY,
            Module.FEATURE_PDF,
            Module.FEATURE_PDFWIZARD,
            Module.FEATURE_RECORDING,
            Module.FEATURE_SETTINGS,
            Module.FEATURE_SQLITE,
            Module.FEATURE_STT -> {
                when (to) {
                    Module.UI_COMMON,
                    Module.UI_ANDROID,
                    Module.UI_DESKTOP,
                    Module.UI_LEGACY,
                    Module.PDF_ANDROID,
                    Module.PDF_DESKTOP,
                    Module.STT_ANDROID,
                    Module.STT_DESKTOP -> DependencyViolation(
                        from,
                        to,
                        "Feature module $from must not depend on UI or platform module $to."
                    )
                    else -> null
                }
            }
            Module.PDF_ANDROID,
            Module.PDF_DESKTOP,
            Module.STT_ANDROID,
            Module.STT_DESKTOP -> {
                when (to) {
                    Module.UI_COMMON,
                    Module.UI_ANDROID,
                    Module.UI_DESKTOP,
                    Module.UI_LEGACY -> DependencyViolation(
                        from,
                        to,
                        "Platform implementation module $from must not depend on UI module $to."
                    )
                    else -> null
                }
            }
            Module.UI_COMMON,
            Module.UI_ANDROID,
            Module.UI_DESKTOP,
            Module.UI_LEGACY -> {
                // UI modules can depend on almost anything
                null
            }
            Module.UNKNOWN -> null
        }
    }
}



/*
object DependencyRules {
    fun isForbidden(from: Module_old, to: Module_old): String? {
        return when (from) {
            Module_old.CORE -> if (to != Module_old.CORE)
                "core must not depend on $to" else null
                Module_old.PDF -> if (to != Module_old.CORE)
                    "pdf must depend only on core" else null
                Module_old.UI_COMMON -> if (to !in listOf(Module_old.CORE,
                    Module_old.PDF)) "ui-common must not depend on $to" else null
            Module_old.ANDROID_UI -> if (to == Module_old.DESKTOP_UI)
                "android-ui must not depend on desktop-ui" else null
            Module_old.DESKTOP_UI -> if (to == Module_old.ANDROID_UI)
                "desktop-ui must not depend on android-ui" else null
            Module_old.APP_ANDROID -> if (to != Module_old.ANDROID_UI)
                "appandroid must depend only on android-ui" else null
            Module_old.APP_DESKTOP -> if (to != Module_old.DESKTOP_UI)
                "appdesktop must depend only on desktop-ui" else null
            else -> null
        }
    }
}
*/
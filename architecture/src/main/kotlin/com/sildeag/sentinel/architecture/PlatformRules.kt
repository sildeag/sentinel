package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid

object PlatformRules {
    fun checkRules(module: EnumModule, ktmodule: String, id: Uuid, import: String):
            ImportViolation? {
        val policy = ModulePolicyTable.policy[module]
        fun forbidden(msg: String, suggestion: String? = null) =
            ImportViolation(module, ktmodule, import, msg, suggestion, id)
        return when (policy) {
            // CORE-LIKE modules forbid all platform imports
            ModulePolicy.CORE_LIKE -> {
                when {
                    PlatformPatterns.android.containsMatchIn(import)
                        ->
                        forbidden("Android API forbidden in $module.",
                    "Move this code to :ui-android, :pdfandroid, or :stt-android.")

                        PlatformPatterns.desktopAwt.containsMatchIn(import) ||

                                PlatformPatterns.desktopSwing.containsMatchIn(import) ->
                    forbidden("Desktop API forbidden in $module.",
                    "Move this code to :ui-desktop or :pdfdesktop.")
                    else -> null
                }
            }
            // PLATFORM_IMPL modules forbid UI imports (handled in ImportRules)
                ModulePolicy.PLATFORM_IMPL -> null
            // FEATURE modules allow platform imports indirectly
            ModulePolicy.FEATURE -> null
            // UI modules forbid platform engines (handled in ImportRules)
                ModulePolicy.UI -> null
            // Legacy UI forbids Android
            ModulePolicy.LEGACY_UI -> {
                if (PlatformPatterns.android.containsMatchIn(import))
                    forbidden("Android API forbidden in JavaFX UI module.")
                        else null
            }
            ModulePolicy.TEST,
            ModulePolicy.UNKNOWN -> null

            else -> null
        }
    }
}
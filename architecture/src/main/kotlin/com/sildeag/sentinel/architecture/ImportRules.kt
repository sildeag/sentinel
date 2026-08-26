package com.sildeag.sentinel.architecture

data class ImportViolation(
    val module: Module,
    val import: String,
    val message: String,
    val suggestion: String? = null
) {
    val reason: String
        get() = suggestion?.let { "$message — $it" } ?: message
}


object ImportRules {

    fun checkImport(module: Module, import: String): String? {
        val violation: ImportViolation? = importCheck(module, import)
        return violation?.reason   // convert to String?
    }

        // Your new rule engine
    private fun importCheck(module: Module, import: String): ImportViolation? {
        // whatever logic you already wrote

        if (module == Module.TEST_HARNESS) return null
        fun forbidden(msg: String, suggestion: String? = null) =
            ImportViolation(module, import, msg, suggestion)
        return when (module) {
            Module.CORE,
            Module.PLATFORM,
            Module.SETTINGS,
            Module.STORAGE,
            Module.DI,
            Module.APP_COMMON -> {
                when {
                    import.startsWith("android.") -> forbidden(
                            "Android API forbidden in $module.",
                            "Move this code to :ui-android, :pdfandroid, or :stt-android."
                        )
                    import.startsWith("androidx.compose.") -> forbidden(
                            "Compose UI forbidden in $module.",
                            "Move this code to :ui-common."
                        )
                    import.startsWith("org.jetbrains.skia.") -> forbidden(
                            "Desktop Skia API forbidden in $module.",
                            "Move this code to :ui-desktop or :pdfdesktop."
                        )
                    import.startsWith("org.apache.pdfbox.") -> forbidden(
                            "PDFBox API forbidden in $module.",
                            "Move this code to :pdf-desktop."
                        )
                    import.startsWith("android.speech.") -> forbidden(
                            "Android STT API forbidden in $module.",
                            "Move this code to :stt-android."
                        )
                    else -> null
                }
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
                when {
                    import.startsWith("androidx.compose.") -> forbidden(
                            "UI function forbidden in feature module $module.",
                    "Move this Composable to :ui-common, :uiandroid, or :ui-desktop."
                        )
                        import.startsWith("android.") -> forbidden(
                        "Android API forbidden in feature module $module.",
                    "Move this code to :ui-android, :pdfandroid, or :stt-android."
                        )
                        import.startsWith("org.jetbrains.skia.") ||
                            import.startsWith("org.apache.pdfbox.") -> forbidden(
                        "Desktop/Skia/PDFBox API forbidden in feature module $module.",
                    "Move this code to :pdf-desktop or :sttdesktop."
                        )
                        import.startsWith("android.speech.") -> forbidden(
                        "Android STT API forbidden in feature module $module.",
                        "Move this code to :stt-android."
                    )
                    else -> null
                }
            }
            Module.PDF_ANDROID -> {
                when {
                    import.startsWith("androidx.compose.") -> forbidden(
                            "Compose UI forbidden in $module.",
                            "Move UI code to :ui-android."
                        )
                    import.startsWith("org.jetbrains.skia.") -> forbidden(
                            "Desktop Skia API forbidden in $module.",
                            "Move this code to :pdf-desktop or :uidesktop."
                        )
                    else -> null
                }
            }
            Module.PDF_DESKTOP -> {
                when {
                    import.startsWith("android.") -> forbidden(
                            "Android API forbidden in $module.",
                            "Move this code to :pdf-android or :uiandroid."
                        )
                    else -> null
                }
            }
            Module.STT_ANDROID -> {
                when {
                    import.startsWith("androidx.compose.") -> forbidden(
                            "Compose UI forbidden in $module.",
                            "Move UI code to :ui-android."
                        )
                    else -> null
                }
            }
            Module.STT_DESKTOP -> {
                when {
                    import.startsWith("android.speech.") -> forbidden(
                            "Android STT API forbidden in $module.",
                            "Move this code to :stt-android."
                        )
                    else -> null
                }
            }
            Module.UI_COMMON,
            Module.UI_ANDROID,
            Module.UI_DESKTOP,
            Module.UI_LEGACY -> {
                when {
                    import.startsWith("org.apache.pdfbox.") ->
                        forbidden(
                            "PDFBox engine forbidden in UI module $module.",
                    "Move this code to :pdf-desktop."
                        )
                        import.startsWith("android.speech.") ->
                    forbidden(
                        "Android STT engine forbidden in UI module $module.",
                        "Move this code to :stt-android."
                    )
                    else -> null
                }
            }
            Module.TEST_HARNESS,
            Module.UNKNOWN -> null
        }
    }


}
/*
object ImportRules {
    fun isForbidden(module: Module_old, line: String): String? {
        return when (module) {
            Module_old.CORE -> when {
                ImportPatterns.compose.containsMatchIn(line) ->
                    "Compose imports forbidden in core"
                ImportPatterns.android.containsMatchIn(line) ->
                    "Android imports forbidden in core"
                ImportPatterns.awt.containsMatchIn(line) ->
                    "Desktop imports forbidden in core"
                else -> null
            }
            Module_old.UI_COMMON -> when {
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

 */
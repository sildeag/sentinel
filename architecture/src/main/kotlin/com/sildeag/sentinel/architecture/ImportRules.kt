package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid

object ImportRules {
    fun checkImport(module: EnumModule, ktmodule: String, id: Uuid, import: String):
            ImportViolation? {
        val policy = ModulePolicyTable.policy[module]
        if (module == EnumModule.TEST_HARNESS) return null
        fun forbidden(msg: String, suggestion: String? = null) =
            ImportViolation(module, ktmodule, import, msg, suggestion, id)
        return when (policy) {
            ModulePolicy.CORE_LIKE -> {
                when {
                    import.startsWith("android.") ->
                        forbidden("Android API forbidden in $module.",
                    "Move this code to :ui-android, :pdfandroid, or :stt-android.")
                        import.startsWith("androidx.compose.") ->
                    forbidden("Compose UI forbidden in $module.",
                        "Move this code to :ui-common.")
                    import.startsWith("org.jetbrains.skia.") ->
                        forbidden("Desktop Skia API forbidden in $module.",
                    "Move this code to :ui-desktop or :pdfdesktop.")
                        import.startsWith("org.apache.pdfbox.") ->
                    forbidden("PDFBox API forbidden in $module.",
                        "Move this code to :pdf-desktop.")
                    import.startsWith("android.speech.") ->
                        forbidden("Android STT API forbidden in $module.",
                    "Move this code to :stt-android.")
                    else -> null
                }
            }
            ModulePolicy.FEATURE -> {
                when {
                    import.startsWith("androidx.compose.") ->
                        forbidden("UI function forbidden in feature module $module.",
                            "Move this Composable to :ui-common, :uiandroid, or :ui-desktop.")
                    import.startsWith("android.") ->
                        forbidden("Android API forbidden in feature module $module.",
                            "Move this code to :ui-android, :pdfandroid, or :stt-android.")
                    import.startsWith("org.jetbrains.skia.") ||
                            import.startsWith("org.apache.pdfbox.") ->
                        forbidden("Desktop/Skia/PDFBox API forbidden in feature module $module.",
                            "Move this code to :pdf-desktop or :sttdesktop.")
                    import.startsWith("android.speech.") ->
                        forbidden("Android STT API forbidden in feature module $module.",
                    "Move this code to :stt-android.")
                    else -> null
                }
            }
            ModulePolicy.PLATFORM_IMPL -> {
                when {
                    module == EnumModule.PDF_ANDROID &&
                            import.startsWith("androidx.compose.") ->
                        forbidden("Compose UI forbidden in $module.",
                            "Move UI code to :ui-android.")
                    module == EnumModule.PDF_ANDROID &&
                            import.startsWith("org.jetbrains.skia.") ->
                        forbidden("Desktop Skia API forbidden in $module.",
                    "Move this code to :pdf-desktop.")
                        module == EnumModule.PDF_DESKTOP &&
                                import.startsWith("android.") ->
                    forbidden("Android API forbidden in $module.",
                    "Move this code to :pdf-android.")
                        module == EnumModule.STT_ANDROID &&
                                import.startsWith("androidx.compose.") ->
                    forbidden("Compose UI forbidden in $module.",
                        "Move UI code to :ui-android.")
                    module == EnumModule.STT_DESKTOP &&
                            import.startsWith("android.speech.") ->
                        forbidden("Android STT API forbidden in $module.",
                    "Move this code to :stt-android.")
                    else -> null
                }
            }
            ModulePolicy.UI,
            ModulePolicy.LEGACY_UI -> {
                when {
                    import.startsWith("org.apache.pdfbox.") ->
                        forbidden("PDFBox engine forbidden in UI module $module.",
                            "Move this code to :pdf-desktop.")
                    import.startsWith("android.speech.") ->
                        forbidden("Android STT engine forbidden in UI module $module.",
                            "Move this code to :stt-android.")
                    else -> null
                }
            }
            ModulePolicy.TEST,
            ModulePolicy.UNKNOWN -> null

            else -> {null}
        }
    }
}

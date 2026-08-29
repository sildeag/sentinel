package com.sildeag.sentinel.architecture
enum class ModulePolicy {
    CORE_LIKE,
    FEATURE,
    PLATFORM_IMPL,
    UI,
    LEGACY_UI,
    TEST,
    UNKNOWN
}
object ModulePolicyTable {
    val policy: Map<EnumModule, ModulePolicy> = mapOf(
        // Core-like modules
        EnumModule.CORE to ModulePolicy.CORE_LIKE,
        EnumModule.PLATFORM to ModulePolicy.CORE_LIKE,
        EnumModule.SETTINGS to ModulePolicy.CORE_LIKE,
        EnumModule.STORAGE to ModulePolicy.CORE_LIKE,
        EnumModule.DI to ModulePolicy.CORE_LIKE,
        EnumModule.APP_COMMON to ModulePolicy.CORE_LIKE,
        // Feature modules
        EnumModule.FEATURE_CAPABILITIES to ModulePolicy.FEATURE,
        EnumModule.FEATURE_FORM to ModulePolicy.FEATURE,
        EnumModule.FEATURE_GENEALOGY to ModulePolicy.FEATURE,
        EnumModule.FEATURE_HISTORY to ModulePolicy.FEATURE,
        EnumModule.FEATURE_PDF to ModulePolicy.FEATURE,
        EnumModule.FEATURE_PDFWIZARD to ModulePolicy.FEATURE,
        EnumModule.FEATURE_RECORDING to ModulePolicy.FEATURE,
        EnumModule.FEATURE_SETTINGS to ModulePolicy.FEATURE,
        EnumModule.FEATURE_SQLITE to ModulePolicy.FEATURE,
        EnumModule.FEATURE_STT to ModulePolicy.FEATURE,
        // Platform implementation modules
        EnumModule.PDF_ANDROID to ModulePolicy.PLATFORM_IMPL,
        EnumModule.PDF_DESKTOP to ModulePolicy.PLATFORM_IMPL,
        EnumModule.STT_ANDROID to ModulePolicy.PLATFORM_IMPL,
        EnumModule.STT_DESKTOP to ModulePolicy.PLATFORM_IMPL,
        // UI modules
        EnumModule.UI_COMMON to ModulePolicy.UI,
        EnumModule.UI_ANDROID to ModulePolicy.UI,
        EnumModule.UI_DESKTOP to ModulePolicy.UI,
        // Legacy UI
        EnumModule.UI_LEGACY to ModulePolicy.LEGACY_UI,
        // Test harness
        EnumModule.TEST_HARNESS to ModulePolicy.TEST,
        // Unknown
        EnumModule.UNKNOWN to ModulePolicy.UNKNOWN
    )
}
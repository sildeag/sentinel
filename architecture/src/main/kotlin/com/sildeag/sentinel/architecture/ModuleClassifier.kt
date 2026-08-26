package com.sildeag.sentinel.architecture

enum class Module {
    CORE,
    PLATFORM,
    SETTINGS,
    STORAGE,
    DI,
    APP_COMMON,
    FEATURE_CAPABILITIES,
    FEATURE_FORM,
    FEATURE_GENEALOGY,
    FEATURE_HISTORY,
    FEATURE_PDF,
    FEATURE_PDFWIZARD,
    FEATURE_RECORDING,
    FEATURE_SETTINGS,
    FEATURE_SQLITE,
    FEATURE_STT,
    PDF_ANDROID,
    PDF_DESKTOP,
    STT_ANDROID,
    STT_DESKTOP,
    UI_COMMON,
    UI_ANDROID,
    UI_DESKTOP,
    UI_LEGACY,
    TEST_HARNESS,
    UNKNOWN
}
fun classifyModule(path: String): Module = when (path) {
    ":core" -> Module.CORE
    ":platform" -> Module.PLATFORM
    ":settings" -> Module.SETTINGS
    ":storage" -> Module.STORAGE
    ":di" -> Module.DI
    ":appcommon" -> Module.APP_COMMON
    ":feature-capabilities" -> Module.FEATURE_CAPABILITIES
    ":feature-form" -> Module.FEATURE_FORM
    ":feature-genealogy" -> Module.FEATURE_GENEALOGY
    ":feature-history" -> Module.FEATURE_HISTORY
    ":feature-pdf" -> Module.FEATURE_PDF
    ":feature-pdfwizard" -> Module.FEATURE_PDFWIZARD
    ":feature-recording" -> Module.FEATURE_RECORDING
    ":feature-settings" -> Module.FEATURE_SETTINGS
    ":feature-sqlite" -> Module.FEATURE_SQLITE
    ":feature-stt" -> Module.FEATURE_STT
    ":pdf-android" -> Module.PDF_ANDROID
    ":pdf-desktop" -> Module.PDF_DESKTOP
    ":stt-android" -> Module.STT_ANDROID
    ":stt-desktop" -> Module.STT_DESKTOP
    ":ui-common" -> Module.UI_COMMON
    ":ui-android" -> Module.UI_ANDROID
    ":ui-desktop" -> Module.UI_DESKTOP
    ":ui-legacy" -> Module.UI_LEGACY
    ":test-harness" -> Module.TEST_HARNESS
    else -> Module.UNKNOWN
}

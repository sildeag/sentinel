package com.sildeag.sentinel.architecture

fun classifyEnumModule(path: String): EnumModule = when (path) {
    ":core" -> EnumModule.CORE
    ":platform" -> EnumModule.PLATFORM
    ":settings" -> EnumModule.SETTINGS
    ":storage" -> EnumModule.STORAGE
    ":di" -> EnumModule.DI
    ":appcommon" -> EnumModule.APP_COMMON
    ":feature-capabilities" -> EnumModule.FEATURE_CAPABILITIES
    ":feature-form" -> EnumModule.FEATURE_FORM
    ":feature-genealogy" -> EnumModule.FEATURE_GENEALOGY
    ":feature-history" -> EnumModule.FEATURE_HISTORY
    ":feature-pdf" -> EnumModule.FEATURE_PDF
    ":feature-pdfwizard" -> EnumModule.FEATURE_PDFWIZARD
    ":feature-recording" -> EnumModule.FEATURE_RECORDING
    ":feature-settings" -> EnumModule.FEATURE_SETTINGS
    ":feature-sqlite" -> EnumModule.FEATURE_SQLITE
    ":feature-stt" -> EnumModule.FEATURE_STT
    ":pdf-android" -> EnumModule.PDF_ANDROID
    ":pdf-desktop" -> EnumModule.PDF_DESKTOP
    ":stt-android" -> EnumModule.STT_ANDROID
    ":stt-desktop" -> EnumModule.STT_DESKTOP
    ":ui-common" -> EnumModule.UI_COMMON
    ":ui-android" -> EnumModule.UI_ANDROID
    ":ui-desktop" -> EnumModule.UI_DESKTOP
    ":ui-legacy" -> EnumModule.UI_LEGACY
    ":test-harness" -> EnumModule.TEST_HARNESS
    else -> EnumModule.UNKNOWN
}

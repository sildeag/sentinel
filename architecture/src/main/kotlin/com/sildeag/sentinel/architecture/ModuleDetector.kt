package com.sildeag.sentinel.architecture

object ModuleDetector {
    fun detect(path: String): Module = when {
        "core" in path -> Module.CORE
        "pdf" in path -> Module.PDF
        "ui-common" in path -> Module.UI_COMMON
        "android-ui" in path -> Module.ANDROID_UI
        "desktop-ui" in path -> Module.DESKTOP_UI
        "app-android" in path -> Module.APP_ANDROID
        "app-desktop" in path -> Module.APP_DESKTOP
        else -> Module.UNKNOWN
    }
}
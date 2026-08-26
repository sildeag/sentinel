package com.sildeag.sentinel.architecture

object ModuleDetector {
    fun detect(path: String): Module_old = when {
        "core" in path -> Module_old.CORE
        "pdf" in path -> Module_old.PDF
        "ui-common" in path -> Module_old.UI_COMMON
        "android-ui" in path -> Module_old.ANDROID_UI
        "desktop-ui" in path -> Module_old.DESKTOP_UI
        "app-android" in path -> Module_old.APP_ANDROID
        "app-desktop" in path -> Module_old.APP_DESKTOP
        else -> Module_old.UNKNOWN
    }
}
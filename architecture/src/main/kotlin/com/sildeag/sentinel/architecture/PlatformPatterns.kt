package com.sildeag.sentinel.architecture

object PlatformPatterns {
    val android = Regex("^import\\s+android\\.")
    val desktopAwt = Regex("^import\\s+java\\.awt\\.")
    val desktopSwing = Regex("^import\\s+javax\\.swing\\.")
}
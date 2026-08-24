package com.sildeag.sentinel.architecture

object ImportPatterns {
    val android = Regex("^import\\s+android\\.")
    val androidx = Regex("^import\\s+androidx\\.")
    val awt = Regex("^import\\s+java\\.awt\\.")
    val swing = Regex("^import\\s+javax\\.swing\\.")
    val compose = Regex("^import\\s+androidx\\.compose\\.")
}
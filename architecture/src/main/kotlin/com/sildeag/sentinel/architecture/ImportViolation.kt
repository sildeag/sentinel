package com.sildeag.sentinel.architecture

data class ImportViolation(
    val module: EnumModule,
    val import: String,
    val message: String,
    val suggestion: String? = null
) {
    val reason: String
        get() = suggestion?.let { "$message — $it" } ?: message
}

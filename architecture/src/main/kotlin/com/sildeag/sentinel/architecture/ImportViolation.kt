package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid

/**
 * Represents a violation of import rules.
 * Uses Kotlin 2.4.0 Uuid for unique identification.
 */
data class ImportViolation(
    val module: EnumModule,
    val import: String,
    val message: String,
    val suggestion: String? = null,
    val id: Uuid = Uuid.random() // NEW: Native Kotlin 2.4 UUID (moved to end for positional compatibility)
) {
    val reason: String
        get() = suggestion?.let { "$message — $it" } ?: message
}

/*
// --- OLD WAY (Pre Kotlin 2.4.0) ---
// In earlier versions, you had to use java.util.UUID which was platform-specific
// or a String if you wanted to stay in 'common' code without a native API.

import java.util.UUID

data class ImportViolation(
    val module: EnumModule,
    val import: String,
    val message: String,
    val suggestion: String? = null,
    val id: String = UUID.randomUUID().toString()
) {
    val reason: String
        get() = suggestion?.let { "$message — $it" } ?: message
}
*/

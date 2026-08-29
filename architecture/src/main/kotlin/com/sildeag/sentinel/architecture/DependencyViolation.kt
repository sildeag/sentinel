package com.sildeag.sentinel.architecture
data class DependencyViolation(
    val from: EnumModule,
    val to: EnumModule,
    val message: String
)
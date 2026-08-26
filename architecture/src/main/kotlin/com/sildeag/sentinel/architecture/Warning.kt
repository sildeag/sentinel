package com.sildeag.sentinel.architecture

data class Warning(
    val module: Module_old,
    val file: String,
    val line: String,
    val reason: String
) {
    constructor(module: Module_old, file: String, line: String, reason: Any) :
            this(
        module,
        file,
        line,
        reason.toString()
    )
}
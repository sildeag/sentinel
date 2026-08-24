package com.sildeag.sentinel.architecture

data class Warning(
    val module: Module,
    val file: String,
    val line: String,
    val reason: String
) {
    constructor(module: Module, file: String, line: String, reason: Any) :
            this(
        module,
        file,
        line,
        reason.toString()
    )
}
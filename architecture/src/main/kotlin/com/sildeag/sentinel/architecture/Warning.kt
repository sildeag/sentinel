package com.sildeag.sentinel.architecture
data class Warning(
    val module: EnumModule,
    val ktmodule: String,
    val line: String,
    val reason: String
) {
    constructor(module: EnumModule, ktmodule: String, line: String,
                reason: Any) :
            this(module, ktmodule, line, reason.toString())
    override fun toString(): String {
        return buildString {
            appendLine("Module: $module")
            appendLine("File: $ktmodule")
            appendLine("Warning:")
            appendLine(" Line: $line")
            appendLine(" Reason: $reason")
        }
    }
}
/*
package com.sildeag.sentinel.architecture

data class Warning(
    val module: EnumModule,
    val file: String,
    val line: String,
    val reason: String
) {
    constructor(module: EnumModule, file: String, line: String, reason: Any) :
            this(
        module,
        file,
        line,
        reason.toString()
    )
}

 */
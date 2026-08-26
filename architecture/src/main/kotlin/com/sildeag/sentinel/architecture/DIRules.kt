package com.sildeag.sentinel.architecture

object DIRules {
    fun isForbidden(module: Module_old, line: String): String? {
        return when (module) {
            Module_old.CORE -> when {
                DIPatterns.implRepo.containsMatchIn(line) ->
                    "DI implementations forbidden in core"
                        DIPatterns.moduleBlock.containsMatchIn(line) ->
                            "DI modules forbidden in core"
                else -> null
            }
            Module_old.UI_COMMON, Module_old.PDF -> when {
                DIPatterns.koin.containsMatchIn(line) ->
                    "DI forbidden in shared modules"
                        DIPatterns.dagger.containsMatchIn(line) ->
                            "DI forbidden in shared modules"
                        DIPatterns.moduleBlock.containsMatchIn(line) ->
                            "DI modules forbidden in shared modules"
                    DIPatterns.implRepo.containsMatchIn(line) ->
                        "DI implementations forbidden in shared modules"
                else -> null
            }
            else -> null
        }
    }
}

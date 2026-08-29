package com.sildeag.sentinel.architecture

object DIRules {

    fun checkImport(module: EnumModule, import: String): ImportViolation? {

        val diAllowed = module == EnumModule.DI || module == EnumModule.UI_COMMON

        if (!diAllowed) {

            when {
                DIPatterns.isDIImport(import) ->
                    return ImportViolation(
                        import = import,
                        message = "DI framework forbidden in $module",
                        suggestion = "Move DI usage to :di or :ui-common.",
                        module = module
                    )

                DIPatterns.isDIConstruct(import) ->
                    return ImportViolation(
                        module = module,
                        import = import,
                        message = "DI construct forbidden in $module",
                        suggestion = "Move DI constructs to :di or :ui-common."
                    )

                DIPatterns.isRepositoryImpl(import) ->
                    return ImportViolation(
                        module = module,
                        import = import,
                        message = "Repository implementation forbidden in $module",
                        suggestion = "Move repository implementations to :di."
                    )

                DIPatterns.isDIModuleBlock(import) ->
                    return ImportViolation(
                        module = module,
                        import = import,
                        message = "DI module block forbidden in $module",
                        suggestion = "Move DI modules to :di."
                    )
            }
        }

        return null
    }
}

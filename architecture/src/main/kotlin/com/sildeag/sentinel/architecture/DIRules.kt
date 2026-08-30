package com.sildeag.sentinel.architecture

import kotlin.uuid.Uuid
object DIRules {

    fun checkRules(module: EnumModule, ktmodule: String, id: Uuid, import: String): ImportViolation? {

        val diAllowed = module == EnumModule.DI || module == EnumModule.UI_COMMON

        if (!diAllowed) {

            when {
                DIPatterns.isDIImport(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "DI framework forbidden in $module",
                        suggestion = "Move DI usage to :di or :ui-common."
                    )

                DIPatterns.isDIConstruct(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "DI construct forbidden in $module",
                        suggestion = "Move DI constructs to :di or :ui-common."
                    )

                DIPatterns.isRepositoryImpl(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "Repository implementation forbidden in $module",
                        suggestion = "Move repository implementations to :di."
                    )

                DIPatterns.isDIModuleBlock(import) ->
                    return ImportViolation(
                        module = module,
                        ktmodule = ktmodule,
                        id = id,
                        import = import,
                        message = "DI module block forbidden in $module",
                        suggestion = "Move DI modules to :di."
                    )
            }
        }

        return null
    }
}

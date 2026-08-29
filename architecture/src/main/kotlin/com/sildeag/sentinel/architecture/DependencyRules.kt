package com.sildeag.sentinel.architecture
object DependencyRules {
    fun checkDependency(from: EnumModule, to: EnumModule):
            DependencyViolation? {
        val fromPolicy = ModulePolicyTable.policy[from]
        val toPolicy = ModulePolicyTable.policy[to]
        if (fromPolicy == ModulePolicy.TEST || toPolicy ==
            ModulePolicy.TEST)
            return null
        when (fromPolicy) {
            ModulePolicy.CORE_LIKE -> {
                if (toPolicy != ModulePolicy.UNKNOWN) {
                    return DependencyViolation(
                        from, to,
                        "Core-like module $from must not depend on project module $to."
                    )
                }
            }
            ModulePolicy.FEATURE -> {
                if (toPolicy in listOf(
                        ModulePolicy.UI,
                        ModulePolicy.LEGACY_UI,
                        ModulePolicy.PLATFORM_IMPL
                    )
                ) {
                    return DependencyViolation(
                        from, to,
                        "Feature module $from must not depend on UI or platform module $to."
                    )
                }
            }
            ModulePolicy.PLATFORM_IMPL -> {
                if (toPolicy in listOf(
                        ModulePolicy.UI,
                        ModulePolicy.LEGACY_UI
                    )
                ) {
                    return DependencyViolation(
                        from, to,
                        "Platform implementation module $from must not depend on UI module $to."
                    )
                }
            }
            ModulePolicy.UI,
            ModulePolicy.LEGACY_UI,
            ModulePolicy.UNKNOWN -> return null


            else -> {return null}
        }
        return null
    }
}

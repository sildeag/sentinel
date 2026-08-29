package com.sildeag.sentinel.architecture

object DIPatterns {

    // Legacy regex support (not used by the rule engine)
    private val singleRegex = Regex("""single<\w+>""")
    private val factoryRegex = Regex("""factory\s*\{""")
    private val getRegex = Regex("""get\(\)""")

    fun matchesLegacyDIConstructs(line: String): Boolean =
        singleRegex.containsMatchIn(line) ||
                factoryRegex.containsMatchIn(line) ||
                getRegex.containsMatchIn(line)

    fun isDIImport(import: String): Boolean =
        import.startsWith("org.koin.") ||
                import.startsWith("dagger.") ||
                import.startsWith("javax.inject.")

    fun isDIConstruct(import: String): Boolean =
        import.contains("single<") ||
                import.contains("factory {") ||
                import.contains("get()") ||
                import.contains("module {") ||
                import.contains("bind") ||
                import.contains("provider")

    fun isRepositoryImpl(import: String): Boolean =
        import.contains("Impl") && import.contains("Repository")

    fun isDIModuleBlock(import: String): Boolean =
        import.contains("module {") ||
                import.contains("loadKoinModules") ||
                import.contains("startKoin")
}
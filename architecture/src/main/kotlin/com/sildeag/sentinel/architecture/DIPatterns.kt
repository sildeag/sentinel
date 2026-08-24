package com.sildeag.sentinel.architecture

object DIPatterns {
    val koin = Regex("^import\\s+org\\.koin")
    val dagger = Regex("^import\\s+dagger")
    val moduleBlock = Regex("module\\s*\\{")
    val interfaceRepo = Regex("interface\\s+.*Repository")
    val implRepo = Regex("class\\s+.*RepositoryImpl")
}
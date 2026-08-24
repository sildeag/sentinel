package com.sildeag.sentinel.architecture

import java.io.File
object ArchitectureSentinel {
    enum class ScanMode { IMPORTS, UI, PLATFORM, THEME, DI, DEPENDENCY, ALL }
    fun run(root: File, mode: ScanMode = ScanMode.ALL) {
        println("Running Sentinel mode: $mode")
        when (mode) {
            ScanMode.IMPORTS -> ImportScanner.scan(root)
            ScanMode.UI -> UIScanner.scan(root)
            ScanMode.PLATFORM -> PlatformScanner.scan(root)
            ScanMode.THEME -> ThemeScanner.scan(root)
            ScanMode.DI -> DIScanner.scan(root)
            ScanMode.DEPENDENCY -> DependencyScanner.scan(root)
            ScanMode.ALL -> {
                ImportScanner.scan(root)
                UIScanner.scan(root)
                PlatformScanner.scan(root)
                ThemeScanner.scan(root)
                DIScanner.scan(root)
                DependencyScanner.scan(root)
            }
        }
    }
}

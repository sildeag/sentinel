package com.sildeag.sentinel.architecture

import java.io.File

fun main(args: Array<String>) {
    // First argument: project root (default ".")
    val rootArg = args.getOrNull(0) ?: "."
    if (rootArg.contains("sentinel", ignoreCase = true)) {
        println("Skipping sentinel project.")
        return
    }

    val root = File(rootArg)

    // Second argument: scan mode (default ALL)
    val modeArg = args.getOrNull(1)?.uppercase()
    val mode = modeArg
        ?.let { runCatching { ArchitectureSentinel.ScanMode.valueOf(it) }.getOrNull() }
        ?: ArchitectureSentinel.ScanMode.ALL

    ArchitectureSentinel.run(root, mode)
}

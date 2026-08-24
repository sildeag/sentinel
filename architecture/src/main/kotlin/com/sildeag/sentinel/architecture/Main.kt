package com.sildeag.sentinel.architecture

import java.io.File
fun main(args: Array<String>) {
    val root = File(args.getOrNull(0) ?: ".")
    val mode = args.getOrNull(1)?.uppercase()?.let {
        ArchitectureSentinel.ScanMode.valueOf(it)
    } ?: ArchitectureSentinel.ScanMode.ALL
    ArchitectureSentinel.run(root, mode)
}
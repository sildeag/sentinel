package com.sildeag.sentinel.architecture

import java.io.File

fun main(args: Array<String>) {

    val rootArg = args.getOrNull(0) ?: "."
    val root = File(rootArg)

    println("Sentinel: starting scan at ${root.absolutePath}")

    val sentinel = ArchitectureSentinel(root)
    sentinel.run()
}

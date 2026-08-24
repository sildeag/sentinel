package com.sildeag.sentinel.architecture

object WarningPrinter {
    fun print(w: Warning) {
        println(" ⚠️ ${w.reason}\n Module: ${w.module}\n File: ${w.file}\n Line: ${w.line}\n")
    }
}
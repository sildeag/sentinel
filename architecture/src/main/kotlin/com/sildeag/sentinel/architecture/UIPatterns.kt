package com.sildeag.sentinel.architecture

object UIPatterns {

    fun isComposable(import: String): Boolean =
        import.startsWith("androidx.compose.") ||
                import.contains("@Composable")

    fun isPreview(import: String): Boolean =
        import.contains("@Preview")

    fun isUIFunction(import: String): Boolean =
        uiFunctions.any { import.contains(it) }

    private val uiFunctions = listOf(
        "Text",
        "Column",
        "Row",
        "Box",
        "LazyColumn",
        "Button",
        "Icon"
    )
}

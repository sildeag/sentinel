package com.sildeag.sentinel.architecture

object UIPatterns {

    fun isComposable(import: String): Boolean =
        import.startsWith("androidx.compose.") ||
                import.contains("@Composable")

    fun isPreview(import: String): Boolean =
        import.contains("@Preview")

    fun isUIFunction(import: String): Boolean =
        uiFunctions.any { 
            // Use word boundaries to prevent matching substrings (e.g., "Sound2Text")
            Regex("\\b${it}\\b").containsMatchIn(import) 
        }

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

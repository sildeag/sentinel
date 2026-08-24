package com.sildeag.sentinel.architecture

object UIPatterns {
    val composable = Regex("@Composable")
    val preview = Regex("@Preview")
    val uiFunctions = listOf("Text(", "Column(", "Row(", "Box(",
        "LazyColumn(", "Button(", "Icon(")
}

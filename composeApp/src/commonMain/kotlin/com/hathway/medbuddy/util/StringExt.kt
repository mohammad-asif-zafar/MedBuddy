package com.hathway.medbuddy.util

fun String.displayName(): String {
    val parts = trim().split("\\s+".toRegex()).filter { it.isNotBlank() }

    return when {
        parts.size >= 3 -> parts[1]              // Middle name
        parts.size == 2 -> "${parts[0]} ${parts[1]}"
        parts.size == 1 -> parts[0]
        else -> ""
    }
}

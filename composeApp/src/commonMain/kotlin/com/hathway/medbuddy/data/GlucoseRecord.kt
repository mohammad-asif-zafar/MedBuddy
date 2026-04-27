package com.hathway.medbuddy.data

import androidx.compose.ui.autofill.ContentDataType.Companion.Date
import java.text.SimpleDateFormat
import java.util.*

data class GlucoseRecord(
    val date: String,
    val fasting: Int?,
    val breakfast: Int?,
    val lunch: Int?,
    val dinner: Int?
)

data class UserGlucoseRecord(
    val date: String,
    val timePeriod: String,
    val value: Int
)

// User-added glucose records (empty initially)
val userGlucoseRecords = mutableListOf<UserGlucoseRecord>()

// Helper function to parse date strings for sorting
private fun parseDemoDate(dateString: String): Date {
    val format = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return try {
        format.parse(dateString) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}

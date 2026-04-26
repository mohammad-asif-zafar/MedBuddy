package com.hathway.medbuddy.data

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

val glucoseDemoList = listOf(
    GlucoseRecord("9 Jan", 185, 180, 158, 140),
    GlucoseRecord("10 Jan", 140, 137, 144, null),
    GlucoseRecord("11 Jan", 156, 196, 178, 100),
    GlucoseRecord("12 Jan", 130, 178, 195, 170),
    GlucoseRecord("13 Jan", 140, 147, 125, 132),
    GlucoseRecord("14 Jan", 153, 168, null, null),
    GlucoseRecord("15 Jan", 141, 187, 165, 147),
    GlucoseRecord("16 Jan", 146, 232, 136, 125),
    GlucoseRecord("17 Jan", 187, 125, 113, 156),
    GlucoseRecord("18 Jan", 157, 129, 55, 140),
    GlucoseRecord("19 Jan", 237, 217, 186, 153),
    GlucoseRecord("20 Jan", 175, 184, 147, null),
    GlucoseRecord("21 Jan", 165, 193, 131, null),
    GlucoseRecord("22 Jan", 170, 191, 157, 185),
    GlucoseRecord("23 Jan", 182, 187, 138, null),
    GlucoseRecord("24 Jan", 187, 211, 208, 147),
    GlucoseRecord("25 Jan", 152, 185, 165, 179),
    GlucoseRecord("26 Jan", 225, 164, 191, 185),
    GlucoseRecord("27 Jan", 137, 210, 131, 205),
    GlucoseRecord("28 Jan", 176, 185, 203, 195),
    GlucoseRecord("29 Jan", 223, 186, 164, 203),
    GlucoseRecord("30 Jan", 164, 182, 139, 207),
    GlucoseRecord("31 Jan", 182, 208, 168, null),

    GlucoseRecord("1 Feb", 140, 234, 275, null),
    GlucoseRecord("2 Feb", 161, 207, 164, 200),
    GlucoseRecord("3 Feb", 177, 223, 193, 207),
    GlucoseRecord("4 Feb", 179, 189, 168, null),
    GlucoseRecord("5 Feb", 153, 213, 197, null),
    GlucoseRecord("6 Feb", 145, null, null, null),
    GlucoseRecord("7 Feb", 157, 205, 192, null),
    GlucoseRecord("8 Feb", 146, null, null, 205),
    GlucoseRecord("9 Feb", 145, null, null, 187)
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

// Convert demo data to UserGlucoseRecord format
fun convertDemoToUserRecords(): List<UserGlucoseRecord> {
    val userRecords = mutableListOf<UserGlucoseRecord>()
    
    glucoseDemoList.forEach { record ->
        // Create a list of available readings for this date
        val availableReadings = mutableListOf<Pair<String, Int>>()
        
        // Add fasting if not null
        record.fasting?.let { value ->
            availableReadings.add("Fasting" to value)
        }
        
        // Add breakfast if not null
        record.breakfast?.let { value ->
            availableReadings.add("After Breakfast" to value)
        }
        
        // Add lunch if not null
        record.lunch?.let { value ->
            availableReadings.add("After Lunch" to value)
        }
        
        // Add dinner if not null
        record.dinner?.let { value ->
            availableReadings.add("After Dinner" to value)
        }
        
        // Add only the first available reading (most important one)
        if (availableReadings.isNotEmpty()) {
            val firstReading = availableReadings.first()
            userRecords.add(
                UserGlucoseRecord(
                    date = record.date,
                    timePeriod = firstReading.first,
                    value = firstReading.second
                )
            )
        }
    }
    
    // Sort demo data by date (latest first)
    return userRecords.sortedByDescending { record ->
        parseDemoDate(record.date)
    }
}

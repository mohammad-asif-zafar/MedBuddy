package com.hathway.medbuddy.data

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

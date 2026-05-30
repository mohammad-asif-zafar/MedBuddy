package com.hathway.medbuddy.data

data class GlucoseRecord(
    val date: String,
    val beforeBreakfast: Int?,
    val afterBreakfast: Int?,
    val beforeLunch: Int?,
    val afterLunch: Int?,
    val beforeDinner: Int?,
    val afterDinner: Int?,
    val bedtime: Int?
)

data class UserGlucoseRecord(
    val date: String,
    val timePeriod: String,
    val value: Int
)

// User-added glucose records (empty initially)
val userGlucoseRecords = mutableListOf<UserGlucoseRecord>()

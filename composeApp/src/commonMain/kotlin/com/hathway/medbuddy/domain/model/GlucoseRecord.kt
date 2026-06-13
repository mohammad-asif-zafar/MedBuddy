package com.hathway.medbuddy.domain.model

data class GlucoseRecord(
    val id : String = "1",
    val date: String,
    val beforeBreakfast: Int?,
    val afterBreakfast: Int?,
    val beforeLunch: Int?,
    val afterLunch: Int?,
    val beforeDinner: Int?,
    val afterDinner: Int?,
    val bedtime: Int?,
    // Firebase fields
    val time: String = "",
    val mealType: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class UserGlucoseRecord(
    val date: String,
    val timePeriod: String,
    val value: Int,
    val time: String = "",
    val mealType: String = "",
    val notes: String = ""
)

// User-added glucose records (empty initially)
val userGlucoseRecords = mutableListOf<UserGlucoseRecord>()

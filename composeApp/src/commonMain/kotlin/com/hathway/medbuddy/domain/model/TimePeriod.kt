package com.hathway.medbuddy.domain.model

enum class TimePeriod {
    BEFORE_BREAKFAST,
    AFTER_BREAKFAST,
    BEFORE_LUNCH,
    AFTER_LUNCH,
    BEFORE_DINNER,
    AFTER_DINNER,
    BEDTIME;

    companion object {
        fun fromString(type: String): TimePeriod {
            return try {
                valueOf(type.uppercase())
            } catch (e: Exception) {
                // Handle common abbreviations or fallbacks
                when (type.uppercase()) {
                    "BBF" -> BEFORE_BREAKFAST
                    "ABF" -> AFTER_BREAKFAST
                    "BL" -> BEFORE_LUNCH
                    "AL" -> AFTER_LUNCH
                    "BD" -> BEFORE_DINNER
                    "AD" -> AFTER_DINNER
                    "BT", "NGT" -> BEDTIME
                    else -> BEFORE_BREAKFAST
                }
            }
        }
    }
}
data class ValidationResult(
    val isInRange: Boolean, val targetMessage: String
)

data class GlucoseTarget(
    val minTarget: Double, val maxTarget: Double, val statusText: String
)


fun getGlucoseRange(period: TimePeriod): Pair<Int, Int> {
    return when (period) {
        TimePeriod.BEFORE_BREAKFAST -> 70 to 100
        TimePeriod.AFTER_BREAKFAST -> 80 to 180
        TimePeriod.BEFORE_LUNCH -> 80 to 130
        TimePeriod.AFTER_LUNCH -> 80 to 180
        TimePeriod.BEFORE_DINNER -> 80 to 130
        TimePeriod.AFTER_DINNER -> 80 to 180
        TimePeriod.BEDTIME -> 100 to 140
    }
}

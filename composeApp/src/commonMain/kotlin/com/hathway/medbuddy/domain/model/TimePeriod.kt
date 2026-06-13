package com.hathway.medbuddy.domain.model

enum class TimePeriod(
    val displayName: String,
    val shortName: String
) {
    BEFORE_BREAKFAST("Before Breakfast", "BBF"),
    AFTER_BREAKFAST("After Breakfast", "ABF"),
    BEFORE_LUNCH("Before Lunch", "BL"),
    AFTER_LUNCH("After Lunch", "AL"),
    BEFORE_DINNER("Before Dinner", "BD"),
    AFTER_DINNER("After Dinner", "AD"),
    BEDTIME("Bedtime", "NGT")
}
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

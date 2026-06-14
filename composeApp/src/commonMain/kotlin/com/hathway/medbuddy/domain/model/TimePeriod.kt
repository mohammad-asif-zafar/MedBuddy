package com.hathway.medbuddy.domain.model

enum class TimePeriod {
    BEFORE_BREAKFAST,
    AFTER_BREAKFAST,
    BEFORE_LUNCH,
    AFTER_LUNCH,
    BEFORE_DINNER,
    AFTER_DINNER,
    BEDTIME
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

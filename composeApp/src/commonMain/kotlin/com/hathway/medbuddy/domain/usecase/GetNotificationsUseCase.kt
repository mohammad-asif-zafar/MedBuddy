package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.util.getNowEpochMillis
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

data class MedBuddyNotification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timestamp: Long = getNowEpochMillis()
)

enum class NotificationType {
    ALERT, REMINDER, INFO, HIGH_GLUCOSE, LOW_GLUCOSE, MEDICATION_REMINDER
}

class GetNotificationsUseCase(
    private val glucoseRepository: IGlucoseRepository,
    private val doctorRepository: IDoctorRepository
) {
    suspend operator fun invoke(): List<MedBuddyNotification> {
        val notifications = mutableListOf<MedBuddyNotification>()

        val allRecords = glucoseRepository.getAllRecords()
        val userId = FirebaseManager.currentUser?.uid ?: ""
        val doctorInfo = if (userId.isNotEmpty()) doctorRepository.getDoctorInfo(userId) else null

        val today = getNowLocalDateTime().date

        // 1. Check for today's logs (Reminders)
        val todayRecord = allRecords.find {
            try {
                parseDisplayDate(it.date) == today
            } catch (e: Exception) {
                false
            }
        }

        if (todayRecord == null) {
            notifications.add(
                MedBuddyNotification(
                    id = "daily_log_reminder",
                    title = getString(Res.string.daily_log_reminder),
                    message = getString(Res.string.daily_log_reminder_desc),
                    type = NotificationType.REMINDER
                )
            )
        } else {
            // Check for specific missing meals (simplified)
            if (todayRecord.beforeBreakfast == null && getNowLocalDateTime().hour > 10) {
                notifications.add(
                    MedBuddyNotification(
                        id = "missed_breakfast_log",
                        title = getString(Res.string.missed_breakfast_log),
                        message = getString(Res.string.missed_breakfast_log_desc),
                        type = NotificationType.REMINDER
                    )
                )
            }
        }

        // 2. High/Low Glucose Alerts (Most recent record)
        val latestRecord = allRecords.maxByOrNull { it.createdAt }
        if (latestRecord != null) {
            val readings = listOfNotNull(
                latestRecord.beforeBreakfast,
                latestRecord.afterBreakfast,
                latestRecord.beforeLunch,
                latestRecord.afterLunch,
                latestRecord.beforeDinner,
                latestRecord.afterDinner,
                latestRecord.bedtime
            )

            if (readings.isNotEmpty()) {
                val lastValue = readings.last()
                if (lastValue > 250) {
                    notifications.add(
                        MedBuddyNotification(
                            id = "high_glucose_alert_${latestRecord.id}",
                            title = getString(Res.string.high_glucose_alert),
                            message = getString(Res.string.high_glucose_alert_desc, lastValue),
                            type = NotificationType.ALERT
                        )
                    )
                } else if (lastValue < 70) {
                    notifications.add(
                        MedBuddyNotification(
                            id = "low_glucose_alert_${latestRecord.id}",
                            title = getString(Res.string.low_glucose_alert),
                            message = getString(Res.string.low_glucose_alert_desc, lastValue),
                            type = NotificationType.ALERT
                        )
                    )
                }
            }
        }

        // 3. Upcoming Appointment
        doctorInfo?.nextAppointment?.let { apptDateStr ->
            if (apptDateStr.isNotBlank()) {
                try {
                    val apptDate = parseDisplayDate(apptDateStr)
                    val daysUntil = today.daysUntil(apptDate)
                    if (daysUntil in 0..3) {
                        notifications.add(
                            MedBuddyNotification(
                                id = "upcoming_appointment_${apptDateStr}",
                                title = getString(Res.string.upcoming_appointment),
                                message = getString(
                                    Res.string.upcoming_appointment_desc,
                                    doctorInfo.doctorName,
                                    daysUntil
                                ),
                                type = NotificationType.INFO
                            )
                        )
                    }
                } catch (e: Exception) {
                }
            }
        }

        return notifications.sortedByDescending { it.timestamp }
    }
}

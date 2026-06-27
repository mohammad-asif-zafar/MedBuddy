package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.components.calendar_components.CalendarGridBloodPressure
import com.hathway.medbuddy.presentation.components.calendar_components.CalendarLegendCard
import com.hathway.medbuddy.presentation.components.calendar_components.MonthOverviewCard
import com.hathway.medbuddy.presentation.components.calendar_components.MonthlyAverageCard
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun BloodPressureCalendarScreen(
    back: () -> Unit,
    // Add real state or mock data hook via parameter bindings
    initialSelectedDate: LocalDate = LocalDate(2024, 5, 20)
) {
    var selectedDate by remember { mutableStateOf(initialSelectedDate) }
    var currentMonth by remember {
        mutableStateOf(
            LocalDate(
                initialSelectedDate.year, initialSelectedDate.month, 1
            )
        )
    }

    DetailedReportWrapper(
        title = "Blood Pressure", onBack = { back() }) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Top Title Headers
            Text(
                text = "Blood Pressure Calendar", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005A54) // Custom dark teal matching design
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Track your daily readings and\nmonitor your heart health.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Month Selector Bar Row
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDF7F5))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentMonth = currentMonth.minus(1, DateTimeUnit.MONTH)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Prev",
                            tint = Color(0xFF005A54)
                        )
                    }

                    Row(
                        modifier = Modifier.clickable { /* Handle dialog dropdown trigger */ },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${
                            currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
                        } ${currentMonth.year}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold, color = Color(0xFF005A54)
                            ))
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color(0xFF005A54)
                        )
                    }

                    IconButton(onClick = {
                        currentMonth = currentMonth.plus(1, DateTimeUnit.MONTH)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next",
                            tint = Color(0xFF005A54)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Modified Calendar Grid Box
            Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {

                CalendarGridBloodPressure(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    dayStatuses = emptyMap(), // Connect your status map here
                    onDateSelected = { selectedDate = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend Label Card block
            CalendarLegendCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Overview Counts segment
            MonthOverviewCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Base Averages card info block
            MonthlyAverageCard()
        }
    }
}


@Preview
@Composable
fun BloodPressureCalendarScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            BloodPressureCalendarScreen(back = {})
        }
    }
}

@Preview
@Composable
fun BloodPressureCalendarScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            BloodPressureCalendarScreen(back = {})
        }
    }
}

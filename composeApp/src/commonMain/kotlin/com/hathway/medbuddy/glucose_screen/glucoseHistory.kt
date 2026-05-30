package com.hathway.medbuddy.glucose_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.data.GlucoseRecord
import kotlinx.datetime.LocalDate

@Composable
fun GlucoseRecordHistory(
    records: List<GlucoseRecord>
) {

    if (records.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No glucose records found"
            )
        }

        return
    }

    var selectedDate by remember {
        mutableStateOf(records.firstOrNull()?.date ?: "")
    }

    val selectedRecord = records.firstOrNull {
        it.date == selectedDate
    } ?: return

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Glucose Journal",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

      //  val currentDate = LocalDate.parse(selectedDate)

        Text(
            text = selectedDate,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp))
        WeekDateSelector(
            dates = records.map {
                parseDisplayDate(it.date)
            },
            selectedDate = parseDisplayDate(selectedDate),
            onDateSelected = {
                selectedDate =
                    "${it.dayOfMonth} ${it.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }} ${it.year}"
            }
        )

        Spacer(Modifier.height(16.dp))

        val readings = listOfNotNull(
            selectedRecord.beforeBreakfast,
            selectedRecord.afterBreakfast,
            selectedRecord.beforeLunch,
            selectedRecord.afterLunch,
            selectedRecord.beforeDinner,
            selectedRecord.afterDinner,
            selectedRecord.bedtime
        )

        //DailySummaryCard(readings)

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            glucoseItem(
                "Before Breakfast", selectedRecord.beforeBreakfast
            )

            glucoseItem(
                "After Breakfast", selectedRecord.afterBreakfast
            )

            glucoseItem(
                "Before Lunch", selectedRecord.beforeLunch
            )

            glucoseItem(
                "After Lunch", selectedRecord.afterLunch
            )

            glucoseItem(
                "Before Dinner", selectedRecord.beforeDinner
            )

            glucoseItem(
                "After Dinner", selectedRecord.afterDinner
            )

            glucoseItem(
                "Bedtime", selectedRecord.bedtime
            )
        }
    }
}

fun LazyListScope.glucoseItem(
    label: String, value: Int?
) {

    if (value != null) {

        item {

            GlucoseReadingCard(
                label = label, value = value
            )
        }
    }
}


@Composable
fun DateSelector(
    dates: List<String>, selectedDate: String, onDateSelected: (String) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(dates) { date ->

            val isSelected = date == selectedDate

            Card(
                modifier = Modifier.width(60.dp).clickable {
                    onDateSelected(date)
                }, colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF3A7AFE)
                    else Color.White
                )
            ) {

                Box(
                    modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = date.takeLast(2), color = if (isSelected) Color.White
                        else Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DailySummaryCard(
    readings: List<Int>
) {

    val avg = if (readings.isNotEmpty()) readings.average().toInt()
    else 0

    val high = readings.maxOrNull() ?: 0
    val low = readings.minOrNull() ?: 0

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            SummaryItem("Avg", avg)
            SummaryItem("High", high)
            SummaryItem("Low", low)
        }
    }
}

@Composable
fun SummaryItem(
    label: String, value: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp
        )

        Text(
            text = label, color = Color.Gray
        )
    }
}

@Composable
fun GlucoseReadingCard(
    label: String,
    value: Int
) {

    val (status, color) = when {
        value < 70 -> "Low" to Color(0xFFE53935)
        value <= 140 -> "Normal" to Color(0xFF34C759)
        else -> "High" to Color(0xFFFF9500)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color, CircleShape)
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = value.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "mg/dL",
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Text(
                    text = status,
                    color = color,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DateChip(
    dayName: String, dayNumber: String, selected: Boolean, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(68.dp).height(96.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF3A7AFE)
            else Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = dayName,
                fontSize = 12.sp,
                color = if (selected) Color.White.copy(alpha = .8f)
                else Color.Gray
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = dayNumber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White
                else Color.Black
            )
        }
    }
}

@Composable
fun WeekDateSelector(
    dates: List<LocalDate>, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(dates) { date ->
            val dayLabel =
                date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
            DateChip(
                dayName = dayLabel,
                dayNumber = date.dayOfMonth.toString(),
                selected = date == selectedDate,
                onClick = {
                    onDateSelected(date)
                })
        }
    }
}

fun parseDisplayDate(date: String): LocalDate {
    // Simple parsing assuming format "d MMMM yyyy" like "9 April 2026"
    val parts = date.split(" ")
    val day = parts[0].toInt()
    val monthName = parts[1].lowercase()
    val year = parts[2].toInt()

    val month = when (monthName) {
        "january" -> 1
        "february" -> 2
        "march" -> 3
        "april" -> 4
        "may" -> 5
        "june" -> 6
        "july" -> 7
        "august" -> 8
        "september" -> 9
        "october" -> 10
        "november" -> 11
        "december" -> 12
        else -> 1
    }

    return LocalDate(year, month, day)
}
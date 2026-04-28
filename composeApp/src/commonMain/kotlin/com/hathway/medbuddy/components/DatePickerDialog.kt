package com.hathway.medbuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    initialDate: LocalDate
) {
    var selectedYear by remember { mutableStateOf(initialDate.year) }
    var selectedMonth by remember { mutableStateOf(initialDate.monthNumber - 1) }
    var selectedDay by remember { mutableStateOf(initialDate.dayOfMonth) }
    
    val currentDate = LocalDate(2024, 1, 1) // Use current date as default
    val currentYear = currentDate.year
    val currentMonth = currentDate.monthNumber - 1
    val currentDay = currentDate.dayOfMonth
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Title
                Text(
                    text = "Select Date",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Month Year Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Month Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Month",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        val months = listOf(
                            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                        )
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            months.forEachIndexed { index, month ->
                                val isSelected = selectedMonth == index
                                
                                Text(
                                    text = month,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected) 
                                        MaterialTheme.colorScheme.onPrimaryContainer 
                                    else 
                                        MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 12.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (isSelected) 
                                                MaterialTheme.colorScheme.primary 
                                            else 
                                                Color.Transparent
                                        )
                                        .padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                    
                    // Day Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Day",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val daysInMonth = getDaysInMonth(selectedYear, selectedMonth)
                            val firstDayOfWeek = getFirstDayOfWeek(selectedYear, selectedMonth)
                            
                            // Create a grid layout for days
                            val days = (1..daysInMonth).toList()
                            val rows = days.chunked(7) // 7 days per week
                            
                            // Add empty spaces for alignment
                            repeat(firstDayOfWeek) {
                                Text(
                                    text = "",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(4.dp)
                                )
                            }
                            
                            rows.forEach { week ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    week.forEach { day ->
                                        val isSelected = selectedDay == day
                                        val isToday = isDateToday(selectedYear, selectedMonth, day)
                                        
                                        Text(
                                            text = day.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = when {
                                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                                isToday -> MaterialTheme.colorScheme.primary
                                                else -> MaterialTheme.colorScheme.onPrimaryContainer
                                            },
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(
                                                    if (isSelected) 
                                                            MaterialTheme.colorScheme.primary 
                                                        else if (isToday)
                                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                                        else 
                                                            Color.Transparent
                                                    )
                                                .padding(vertical = 4.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Year Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Year: $selectedYear",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Row {
                        IconButton(
                            onClick = { 
                                if (selectedYear > 2020) selectedYear--
                            },
                            enabled = selectedYear > 2020
                        ) {
                            Text("-")
                        }
                        
                        IconButton(
                            onClick = { 
                                if (selectedYear < currentYear + 1) selectedYear++
                            },
                            enabled = selectedYear < currentYear + 1
                        ) {
                            Text("+")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            val selectedDate = LocalDate(
                                year = selectedYear,
                                monthNumber = selectedMonth + 1,
                                dayOfMonth = selectedDay
                            )
                            onDateSelected(selectedDate)
                        },
                        modifier = Modifier.weight(1f),
                        enabled = selectedDay > 0
                    ) {
                        Text("Select")
                    }
                }
            }
        }
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    val daysInMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    // Simple leap year calculation
    return if (month == 1 && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else daysInMonth[month]
}

private fun getFirstDayOfWeek(year: Int, month: Int): Int {
    // Simple implementation - using Zeller's congruence approximation
    val adjustedMonth = if (month < 3) month + 12 else month
    val adjustedYear = if (month < 3) year - 1 else year
    val dayOfWeek = (1 + (13 * (adjustedMonth + 1)) / 5 + adjustedYear + adjustedYear / 4 - adjustedYear / 100 + adjustedYear / 400) % 7
    return ((dayOfWeek + 6) % 7) // Adjust to make Sunday = 0
}

private fun isDateToday(year: Int, month: Int, day: Int): Boolean {
    val currentDate = LocalDate(2024, 1, 1) // Use current date as default
    return year == currentDate.year && month == currentDate.monthNumber - 1 && day == currentDate.dayOfMonth
}

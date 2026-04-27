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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    initialDate: Date
) {
    var selectedYear by remember { mutableStateOf(SimpleDateFormat("yyyy", Locale.getDefault()).format(initialDate).toInt()) }
    var selectedMonth by remember { mutableStateOf(SimpleDateFormat("MM", Locale.getDefault()).format(initialDate).toInt() - 1) }
    var selectedDay by remember { mutableStateOf(SimpleDateFormat("dd", Locale.getDefault()).format(initialDate).toInt()) }
    
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    
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
                            val selectedDate = Calendar.getInstance().apply {
                                set(Calendar.YEAR, selectedYear)
                                set(Calendar.MONTH, selectedMonth)
                                set(Calendar.DAY_OF_MONTH, selectedDay)
                            }.time
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
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

private fun getFirstDayOfWeek(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> 0
        Calendar.MONDAY -> 1
        Calendar.TUESDAY -> 2
        Calendar.WEDNESDAY -> 3
        Calendar.THURSDAY -> 4
        Calendar.FRIDAY -> 5
        Calendar.SATURDAY -> 6
        else -> 0
    }
}

private fun isDateToday(year: Int, month: Int, day: Int): Boolean {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    return year == currentYear && month == currentMonth && day == currentDay
}

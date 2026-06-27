package com.hathway.medbuddy.presentation.ui.detailed_reports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.insight_glucose_improved
import medbuddy.composeapp.generated.resources.insight_lunch_spike
import medbuddy.composeapp.generated.resources.insight_no_lows
import medbuddy.composeapp.generated.resources.insight_within_range
import medbuddy.composeapp.generated.resources.title_ai_insights
import medbuddy.composeapp.generated.resources.title_doctor_appointments
import medbuddy.composeapp.generated.resources.title_emergency_alerts
import medbuddy.composeapp.generated.resources.title_exercise
import medbuddy.composeapp.generated.resources.title_health_reports
import medbuddy.composeapp.generated.resources.title_meal_tracking
import medbuddy.composeapp.generated.resources.title_medication
import medbuddy.composeapp.generated.resources.title_weight_bmi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedReportWrapper(
    title: String,
    onBack: (() -> Unit)? = null,
    showRightAction: Boolean = false,
    rightIcon: ImageVector? = null,
    onRightActionClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp))
                    }
                },
                actions = {
                    if (showRightAction && rightIcon != null && onRightActionClick != null) {
                        IconButton(onClick = onRightActionClick) {
                            Icon(
                                imageVector = rightIcon,
                                contentDescription = "Toolbar Option Action"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        // Use a standard Modifier without horizontal padding here so the divider spans full-width
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Visual separator line directly beneath the TopAppBar
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )

            // Reapply horizontal padding specifically to the content area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                content()
            }
        }
    }
}



@Composable
fun AIInsightsScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_ai_insights), onBack) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.3f
                        )
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Weekly Summary",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(Res.string.insight_glucose_improved),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item {
                Text(
                    "Smart Trends",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(
                listOf(
                    Res.string.insight_lunch_spike,
                    Res.string.insight_within_range,
                    Res.string.insight_no_lows
                )
            ) { res ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.AutoAwesome,
                            null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(stringResource(res), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun MealTrackingScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_meal_tracking), onBack) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Text(
                    "Today's Carbs",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { 45f / 150f },
                    modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "45 / 150g (Goal)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            item {
                Text(
                    "Meal History",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(listOf("Breakfast", "Lunch", "Dinner")) { meal ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(meal, fontWeight = FontWeight.Bold)
                            Text(
                                "Logged at 10:00 AM",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            "32g Carbs",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationAdherenceScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_medication), onBack) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(24.dp)) {
                CircularProgressIndicator(
                    progress = { 0.92f },
                    modifier = Modifier.size(150.dp),
                    strokeWidth = 12.dp,
                    color = StatusInRange
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("92%", fontSize = 32.sp, fontWeight = FontWeight.Black)
                    Text("Monthly Goal", style = MaterialTheme.typography.bodySmall)
                }
            }

            Text(
                "Upcoming Medications",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(12.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MedicalServices, null, tint = Color.Red)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Metformin", fontWeight = FontWeight.Bold)
                        Text(
                            "1 tablet • Before Breakfast",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HealthReportsDetailScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_health_reports), onBack) {
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.PictureAsPdf, null)
            Spacer(Modifier.width(8.dp))
            Text("Generate Monthly Report")
        }
        Spacer(Modifier.height(24.dp))
        Text("Previous Reports", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        repeat(3) {
            OutlinedCard(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Description, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(12.dp))
                    Text("Report_May_2026.pdf", modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Download, null)
                }
            }
        }
    }
}


@Composable
fun EmergencyAlertsScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_emergency_alerts), onBack) {
        Text("Alert Settings", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.NotificationsActive, null, tint = Color.Red)
            Spacer(Modifier.width(12.dp))
            Text("Notify contacts on high glucose", modifier = Modifier.weight(1f))
            Switch(checked = true, onCheckedChange = {})
        }
        Spacer(Modifier.height(24.dp))
        Text("Emergency Contacts", fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Call, null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Sumit Zafar", fontWeight = FontWeight.Bold)
                    Text("+91 9876543210", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun ExerciseTrackingScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_exercise), onBack) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Steps Today", color = Color.White.copy(alpha = 0.8f))
                Text("7,842", fontSize = 48.sp, fontWeight = FontWeight.Black, color = Color.White)
                Text("Goal: 10,000", color = Color.White.copy(alpha = 0.8f))
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Weekly Progress", fontWeight = FontWeight.Bold)
        // Mock bar chart would go here
    }
}

@Composable
fun WeightBMIScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_weight_bmi), onBack) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Weight", style = MaterialTheme.typography.labelSmall)
                    Text("72 kg", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BMI", style = MaterialTheme.typography.labelSmall)
                    Text("24.2", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Composable
fun DoctorAppointmentsDetailScreen(onBack: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_doctor_appointments), onBack) {
        Text("Next Appointment", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Dr. Sumit Gulla", fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text("Endocrinologist", color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(16.dp))
                Row {
                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("17 July 2026, 10:30 AM")
                }
            }
        }
    }
}

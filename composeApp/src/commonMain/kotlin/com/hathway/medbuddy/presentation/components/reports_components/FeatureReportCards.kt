package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.*
import com.hathway.medbuddy.ThemeMode
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Inner content box (the visual preview)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    content()
                }
            }
        }
    }
}

// 1. AI Insights Card
@Composable
fun AIInsightsCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_ai_insights),
        description = stringResource(Res.string.desc_ai_insights),
        icon = Icons.Outlined.Psychology,
        onClick = onClick
    ) {
        Text(
            stringResource(Res.string.label_glucose_prediction),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(Res.string.label_next_24h), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        // Simple mock chart line
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

// 2. Smart Meal Tracking Card
@Composable
fun MealTrackingCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_meal_tracking),
        description = stringResource(Res.string.desc_meal_tracking),
        icon = Icons.Outlined.Restaurant,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_carbs_today), style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            stringResource(Res.string.format_carbs, 45, 150),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { 45f / 150f },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    }
}

// 3. Medication Adherence Card
@Composable
fun MedicationAdherenceCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_medication),
        description = stringResource(Res.string.desc_medication),
        icon = Icons.Outlined.Medication,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_adherence), style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { 0.92f },
                modifier = Modifier.size(60.dp),
                strokeWidth = 6.dp,
                color = StatusInRange,
                trackColor = StatusInRange.copy(alpha = 0.1f)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("92%", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(Res.string.label_excellent), fontSize = 8.sp, color = StatusInRange)
            }
        }
    }
}

// 4. Health Reports Card
@Composable
fun HealthReportsCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_health_reports),
        description = stringResource(Res.string.desc_health_reports),
        icon = Icons.Outlined.Description,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_glucose_summary), style = MaterialTheme.typography.labelSmall)
        Text("14 - 15 Jun 2026", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ListAlt,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )
        Text("PDF", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Red)
    }
}

// 5. Family Care Card
@Composable
fun FamilyCareCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_family_care),
        description = stringResource(Res.string.desc_family_care),
        icon = Icons.Outlined.Groups,
        onClick = onClick
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) {
            repeat(3) {
                Surface(
                    modifier = Modifier.size(32.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface)
                ) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.padding(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {},
            modifier = Modifier.height(32.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(Res.string.action_add_member), fontSize = 10.sp)
        }
    }
}

// 6. Emergency Alerts Card
@Composable
fun EmergencyAlertsCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_emergency_alerts),
        description = stringResource(Res.string.desc_emergency_alerts),
        icon = Icons.Outlined.NotificationsActive,
        onClick = onClick
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AlertItem(stringResource(Res.string.label_high_glucose), "210 mg/dL", StatusLow)
            AlertItem(stringResource(Res.string.label_low_glucose), "60 mg/dL", StatusHigh)
        }
    }
}

@Composable
fun AlertItem(label: String, value: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Warning, null, tint = color, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
                Text(value, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

// 7. Exercise Tracking
@Composable
fun ExerciseTrackingCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_exercise),
        description = stringResource(Res.string.desc_exercise),
        icon = Icons.AutoMirrored.Outlined.DirectionsRun,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_steps_today), style = MaterialTheme.typography.labelSmall)
        Text("7,842", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(30.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(10) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height((10..30).random().dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

// 8. Weight & BMI
@Composable
fun WeightBMICard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_weight_bmi),
        description = stringResource(Res.string.desc_weight_bmi),
        icon = Icons.Outlined.Scale,
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(stringResource(Res.string.label_weight_inner), style = MaterialTheme.typography.labelSmall)
                Text("72 kg", fontWeight = FontWeight.Bold)
                Text(stringResource(Res.string.format_weight_change, "-1.5"), fontSize = 8.sp, color = StatusInRange)
            }
            // Mini chart line
            Icon(Icons.AutoMirrored.Outlined.ShowChart, null, tint = StatusInRange, modifier = Modifier.size(40.dp))
        }
    }
}

// 9. Blood Pressure
@Composable
fun BloodPressureTrackingCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_blood_pressure),
        description = stringResource(Res.string.desc_blood_pressure),
        icon = Icons.Outlined.FavoriteBorder,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_blood_pressure_inner), style = MaterialTheme.typography.labelSmall)
        Text("120/80", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = StatusInRange)
        Text("mmHg", fontSize = 10.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Surface(color = StatusInRange.copy(alpha = 0.1f), shape = CircleShape) {
            Text(stringResource(Res.string.normal), modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 10.sp, color = StatusInRange, fontWeight = FontWeight.Bold)
        }
    }
}

// 11. Doctor Appointments
@Composable
fun DoctorAppointmentsCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_doctor_appointments),
        description = stringResource(Res.string.desc_doctor_appointments),
        icon = Icons.Outlined.CalendarToday,
        onClick = onClick
    ) {
        Text(stringResource(Res.string.label_next_visit_inner), style = MaterialTheme.typography.labelSmall)
        Text("17 July 2026", fontWeight = FontWeight.Bold)
        Text("10:30 AM", fontSize = 12.sp)
        Text("Dr. Sumit Gulla", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
    }
}

// 12. Dark Mode Feature
@Composable
fun DarkModeFeatureCard(onClick: () -> Unit) {
    FeatureCard(
        title = stringResource(Res.string.title_dark_mode_feature),
        description = stringResource(Res.string.desc_dark_mode_feature),
        icon = Icons.Outlined.DarkMode,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1A1C1E)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Bedtime, null, tint = Color.White)
                // Switch mock
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(20.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(modifier = Modifier.padding(2.dp).size(16.dp).clip(CircleShape).background(Color.White))
                }
            }
        }
    }
}

@Preview
@Composable
fun FeatureReportCardsPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            AIInsightsCard(onClick = {})
            MealTrackingCard(onClick = {})
            MedicationAdherenceCard(onClick = {})
            HealthReportsCard(onClick = {})
            FamilyCareCard(onClick = {})
            EmergencyAlertsCard(onClick = {})
            ExerciseTrackingCard(onClick = {})
            WeightBMICard(onClick = {})
            BloodPressureTrackingCard(onClick = {})
            DoctorAppointmentsCard(onClick = {})
            DarkModeFeatureCard(onClick = {})
        }
    }
}

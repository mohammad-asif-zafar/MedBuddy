package com.hathway.medbuddy.dashboard_home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PatientGreetingCard(
    greeting: String, patientName: String, condition: String
) {
    Column {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = patientName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = condition,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
@Preview
fun PatientGreetingCardSmallPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            PatientGreetingCard(
                greeting = "Good Afternoon",
                patientName = "Shagufta Zafar",
                condition = "Type 2 Diabetes"
            )
        }
    }
}

@Preview(
    name = "Large Phone",
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun PatientGreetingCardLargePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            PatientGreetingCard(
                greeting = "Good Afternoon",
                patientName = "Shagufta Zafar",
                condition = "Type 2 Diabetes"
            )
        }
    }
}


@Preview(
    name = "Small",
    widthDp = 360,
    heightDp = 200
)
@Preview(
    name = "Medium",
    widthDp = 412,
    heightDp = 220
)
@Preview(
    name = "Tablet",
    widthDp = 800,
    heightDp = 250
)
@Composable
fun PatientGreetingCardPreview() {
    MaterialTheme {
        PatientGreetingCard(
            greeting = "Good Afternoon",
            patientName = "Shagufta Zafar",
            condition = "Type 2 Diabetes"
        )
    }
}
package com.hathway.medbuddy.dashboard_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PatientGreetingCard(
    greeting: String,
    patientName: String,
    patientEmail: String = "",
    patientPhotoUrl: String = "",
    condition: String
) {

    Row(
        modifier = Modifier.padding(
            horizontal = 4.dp, vertical = 12.dp
        ), verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = patientName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        Spacer(modifier = Modifier.padding(4.dp))

        if (patientPhotoUrl.isNotEmpty()) {

            AsyncImage(
                model = patientPhotoUrl,
                contentDescription = null,
                modifier = Modifier.size(72.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        } else {

            Box(
                modifier = Modifier.size(72.dp).clip(CircleShape).background(
                    MaterialTheme.colorScheme.primaryContainer
                ), contentAlignment = Alignment.Center
            ) {

                Text(
                    text = patientName.firstOrNull()?.uppercase() ?: "P",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
@Preview
fun PatientGreetingCardPreview() {
    MaterialTheme {
        PatientGreetingCard(
            greeting = "Good Afternoon",
            patientName = "Shagufta Zafar",
            patientEmail = "shagufta@example.com",
            condition = "Type 2 Diabetes"
        )
    }
}

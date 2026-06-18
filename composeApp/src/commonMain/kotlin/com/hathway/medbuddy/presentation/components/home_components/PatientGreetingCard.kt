package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.util.displayName
import com.hathway.medbuddy.util.formatDisplayDate
import com.hathway.medbuddy.util.getGreetingIcon
import com.hathway.medbuddy.util.getGreetingIconImageVector
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.greetingIconColor

@Composable
fun PatientGreetingCard(
    greeting: String, patientName: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFDF9F2)
        ),
        border = BorderStroke(
            1.dp, Color(0xFFE9E1D3)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    Text(
                        text = greeting,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = patientName.displayName()+"  ",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        imageVector = getGreetingIconImageVector(),
                        contentDescription = null,
                        tint = greetingIconColor(),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Today • ${
                        formatDisplayDate(getNowLocalDateTime().date)
                    }",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
            greeting = "Good Afternoon", patientName = "Shagufta Zafar"
        )
    }
}

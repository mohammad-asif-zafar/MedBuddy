package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode

@Composable
fun SectionHeader(
    title: String, icon: ImageVector? = null
) {

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 6.dp)
    ) {

        icon?.let {
            Surface(
                shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.width(34.dp).height(34.dp).padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun SectionHeaderPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        SectionHeader(
            title = "Personal Details",
            icon = Icons.Default.Person
        )
    }
}

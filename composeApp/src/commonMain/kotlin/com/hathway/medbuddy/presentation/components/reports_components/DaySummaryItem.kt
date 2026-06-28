package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.average
import medbuddy.composeapp.generated.resources.glucose_unit_mg_dl
import medbuddy.composeapp.generated.resources.glucose_value_format
import medbuddy.composeapp.generated.resources.ic_calendar_check
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DaySummaryItem(
    title: String,
    date: String,
    value: Int,
    icon: DrawableResource,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    subtextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Surface(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(12.dp), 
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween, 
                modifier = Modifier.fillMaxHeight()
            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = accentColor
                    )
                    Text(
                        text = date,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stringResource(Res.string.average),
                        fontSize = 10.sp,
                        color = subtextColor
                    )
                    Text(
                        text = stringResource(Res.string.glucose_value_format, value),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                    Text(
                        text = stringResource(Res.string.glucose_unit_mg_dl),
                        fontSize = 10.sp,
                        color = subtextColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DaySummaryItemPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            DaySummaryItem(
                title = "Best Day",
                date = "June 25, 2026",
                value = 110,
                icon = Res.drawable.ic_calendar_check
            )
        }
    }
}

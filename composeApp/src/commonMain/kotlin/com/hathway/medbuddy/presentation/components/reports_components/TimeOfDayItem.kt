package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.StatusLow
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.before_breakfast
import medbuddy.composeapp.generated.resources.glucose_unit_mg_dl
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimeOfDayItem(
    modifier: Modifier = Modifier, label: String, value: Int, icon: ImageVector, iconColor: Color
) {
    val healthyColor = StatusInRange
    val warningColor = StatusLow

    val isHigh = value > 140
    val valueDisplayColor = if (isHigh) warningColor else healthyColor

    Surface(
        modifier = modifier.height(115.dp).fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = label,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$value",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueDisplayColor
                )
                Text(
                    text = stringResource(Res.string.glucose_unit_mg_dl),
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

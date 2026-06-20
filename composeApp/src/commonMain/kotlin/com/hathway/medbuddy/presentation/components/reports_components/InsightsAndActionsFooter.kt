package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun InsightsAndActionsFooter(
    insights: List<Pair<DrawableResource, StringResource>>,
    modifier: Modifier = Modifier,
    titleColor: Color = OnSurface,
    textColor: Color = OnSurfaceVariant,
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MiniCardBackground
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.insights_section_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
            Spacer(modifier = Modifier.height(12.dp))

            insights.forEach { insight ->
                val iconDrawable = insight.first
                val stringResourceKey = insight.second

                // Robust color selection logic
                val dynamicIconColor = when (iconDrawable) {
                    Res.drawable.ic_circle_arrow_up -> Warning
                    Res.drawable.ic_shield_check -> Info
                    else -> Success
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        painter = painterResource(iconDrawable),
                        contentDescription = null,
                        tint = dynamicIconColor,
                        modifier = Modifier.padding(top = 2.dp).size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(stringResourceKey),
                        fontSize = 14.sp,
                        color = textColor,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (insights.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_clinical_insights),
                    fontSize = 13.sp,
                    color = textColor.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

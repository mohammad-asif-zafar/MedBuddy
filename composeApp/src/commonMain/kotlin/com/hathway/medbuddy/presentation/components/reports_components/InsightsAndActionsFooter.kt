/*
package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InsightsAndActionsFooter(
    insights: List<Pair<ImageVector, String>>, onExportPdf: () -> Unit, onShareReport: () -> Unit
) {
    val brandGreen = Color(0xFF1B5E20)

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7EE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Insights", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            // Render text bullet items dynamically
            insights.forEach { insight ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        insight.first,
                        contentDescription = null,
                        tint = brandGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(insight.second, fontSize = 13.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sticky Action Button Footer Area
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onExportPdf,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, brandGreen)
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = brandGreen)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Export PDF", color = brandGreen, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onShareReport,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Report", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
*/


package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.btn_export_pdf
import medbuddy.composeapp.generated.resources.btn_share_report
import medbuddy.composeapp.generated.resources.insights_section_title
import org.jetbrains.compose.resources.stringResource
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.CircularProgressIndicator
import com.hathway.medbuddy.presentation.theme.OnPrimary
import com.hathway.medbuddy.presentation.theme.OnSurface
import com.hathway.medbuddy.presentation.theme.OnSurfaceVariant
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.presentation.theme.SurfaceVariant

@Composable
fun InsightsAndActionsFooter(
    insights: List<Pair<ImageVector, String>>,
    onExportPdf: () -> Unit,
    onShareReport: () -> Unit,
    modifier: Modifier = Modifier,
    isExportingPdf: Boolean = false,       // 🔄 Tracks PDF loader animation state
    isSharingReport: Boolean = false,     // 🔄 Tracks Share loader animation state
    containerColor: Color = SurfaceVariant,
    titleColor: Color = OnSurface,
    textColor: Color = OnSurfaceVariant,
    brandAccentColor: Color = Primary,
    buttonContentColor: Color = OnPrimary
) {
    // Buttons are disabled if the list is empty OR if either action is currently processing
    val areButtonsEnabled = insights.isNotEmpty() && !isExportingPdf && !isSharingReport

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
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
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = insight.first,
                        contentDescription = null,
                        tint = brandAccentColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = insight.second, fontSize = 13.sp, color = textColor
                    )
                }
            }

            if (insights.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
            } else {
                Text(
                    text = "No clinical insights available for the selected timeframe.",
                    fontSize = 13.sp,
                    color = textColor.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Sticky Action Button Footer Area
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Export PDF Button Layout
                OutlinedButton(
                    onClick = onExportPdf,
                    enabled = areButtonsEnabled,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (areButtonsEnabled) brandAccentColor else MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.12f
                        )
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = brandAccentColor,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = isExportingPdf, enter = fadeIn(), exit = fadeOut()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = brandAccentColor,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        // Hide standard icon when loading spinner is active
                        if (!isExportingPdf) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf, contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }

                        Text(
                            text = stringResource(Res.string.btn_export_pdf),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 2. Share Report Button Layout
                Button(
                    onClick = onShareReport,
                    enabled = areButtonsEnabled,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = brandAccentColor,
                        contentColor = buttonContentColor,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = isSharingReport, enter = fadeIn(), exit = fadeOut()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = buttonContentColor,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }

                        if (!isSharingReport) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        Text(
                            text = stringResource(Res.string.btn_share_report),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InsightsPreviewTheme(
    isDark: Boolean = false, isCream: Boolean = false, content: @Composable () -> Unit
) {
    val colors = when {
        isCream -> lightColorScheme(
            surfaceVariant = Color(0xFFF7F7EE),      // Warm cream container surface
            onSurface = Color(0xFF1C1B12),           // Dark title text
            onSurfaceVariant = Color(0xFF4A4940),    // Charcoal body text
            primary = Color(0xFF1B5E20),             // Balanced brand green
            onPrimary = Color(0xFFFFFFFF)
        )

        isDark -> darkColorScheme(
            surfaceVariant = Color(0xFF1E1E1C),      // Muted nighttime canvas background
            onSurface = Color(0xFFE6E6E1),           // Bright off-white heading
            onSurfaceVariant = Color(0xFFB5B5AF),    // Greyed descriptive body text
            primary = Color(0xFF81C784),             // High contrast pastel green
            onPrimary = Color(0xFF1B5E20)
        )

        else -> lightColorScheme(
            surfaceVariant = Color(0xFFF5F5F5),
            onSurface = Color(0xFF212121),
            onSurfaceVariant = Color(0xFF666666),
            primary = Color(0xFF1B5E20),
            onPrimary = Color(0xFFFFFFFF)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Preview
@Composable
fun InsightsAndActionsCreamPreview() {
    InsightsPreviewTheme(isCream = true) {
        InsightsAndActionsFooter(
            insights = mockInsights,
            onExportPdf = {},
            onShareReport = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun InsightsAndActionsDarkPreview() {
    InsightsPreviewTheme(isDark = true) {
        InsightsAndActionsFooter(
            insights = mockInsights,
            onExportPdf = {},
            onShareReport = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun InsightsAndActionsLightPreview() {
    InsightsPreviewTheme(isDark = false) {
        InsightsAndActionsFooter(
            insights = mockInsights,
            onExportPdf = {},
            onShareReport = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

private val mockInsights = listOf(
    Icons.Default.Lightbulb to "Glucose levels are 12% more stable than last week.",
    Icons.Default.TrendingDown to "Fasting averages dropped closer to your target line."
)

@Preview
@Composable
fun InsightsAndActionsLoadingPreview() {
    InsightsPreviewTheme(isDark = false) {
        InsightsAndActionsFooter(
            insights = mockInsights,
            onExportPdf = {},
            onShareReport = {},
            isExportingPdf = true, // 👈 Setting this true forces layout container evaluation
            isSharingReport = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}


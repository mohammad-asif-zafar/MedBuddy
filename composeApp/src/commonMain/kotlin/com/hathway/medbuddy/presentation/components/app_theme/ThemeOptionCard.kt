package com.hathway.medbuddy.presentation.components.app_theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.ThemeOptionData
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.Surface
import com.hathway.medbuddy.presentation.ui.ThemeIllustration
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.accessibility_select_theme
import medbuddy.composeapp.generated.resources.theme_dark_desc
import medbuddy.composeapp.generated.resources.theme_dark_title
import medbuddy.composeapp.generated.resources.theme_light_desc
import medbuddy.composeapp.generated.resources.theme_light_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThemeOptionCard(
    option: ThemeOptionData, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    // Styling states calculated dynamically based on layout tokens
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
            alpha = 0.2f
        )
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
    val resolvedTitle = option.title
    val accessibilityLabel = stringResource(Res.string.accessibility_select_theme, resolvedTitle)
    Card(
        onClick = onClick, // FIX: Moves click handling to official Card parameter for correct elevation/ripple clipping
        modifier = modifier.width(160.dp).height(IntrinsicSize.Min).semantics {
            role =
                Role.RadioButton // ACCESSIBILITY: Informs screen readers this is a selectable state
// 2. Pass the fully localized format string to the screen reader engine
            this.onClick(label = accessibilityLabel, action = { onClick(); true })
        },
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(borderWidth, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon Container
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = null, // Left null since title below describes it clearly
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title Block
            Text(
                text = option.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description Block
            Text(
                text = option.description,
                style = MaterialTheme.typography.bodySmall, // FIX: Removed hardcoded text scaling bugs
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f, fill = false), // Flexibly occupies middle spacing
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Illustration Graphic View
            ThemeIllustration(mode = option.mode)
        }
    }
}



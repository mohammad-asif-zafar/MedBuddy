package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import kotlinx.coroutines.launch
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingPage(
    val title: StringResource,
    val description: StringResource,
    val icon: Any // ImageVector or DrawableResource
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            Res.string.onboarding_title_1,
            Res.string.onboarding_desc_1,
            Res.drawable.splash_logo_light
        ),
        OnboardingPage(
            Res.string.onboarding_title_2,
            Res.string.onboarding_desc_2,
            KmpComposeIcons.HeartPulse
        ),
        OnboardingPage(
            Res.string.onboarding_title_3,
            Res.string.onboarding_desc_3,
            Icons.Default.Notifications
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onFinished) {
                    Text(
                        text = stringResource(Res.string.onboarding_skip),
                        color = Color(0xFF00897B),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page Indicator
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(pages.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color(0xFF00897B) else Color.LightGray.copy(alpha = 0.5f)
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onFinished()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00897B)
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == pages.size - 1) 
                            stringResource(Res.string.onboarding_get_started) 
                        else stringResource(Res.string.onboarding_next),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { pageIndex ->
            OnboardingPageContent(pages[pageIndex])
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Illustration Container
        Box(
            modifier = Modifier
                .size(260.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0F2F1).copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            when (val icon = page.icon) {
                is DrawableResource -> {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp),
                        tint = Color.Unspecified
                    )
                }
                is ImageVector -> {
                    Box(contentAlignment = Alignment.Center) {
                        Surface(
                            modifier = Modifier.size(120.dp),
                            shape = CircleShape,
                            color = Color(0xFF00897B).copy(alpha = 0.1f)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color(0xFF00897B),
                                modifier = Modifier.padding(24.dp)
                            )
                        }
                        
                        // Add some decorative checks if it's the notification screen
                        if (icon == Icons.Default.Notifications) {
                             Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF00897B),
                                modifier = Modifier.size(32.dp).align(Alignment.BottomEnd).offset(x = (-8).dp, y = (-8).dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D40),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        OnboardingScreen(onFinished = {})
    }
}

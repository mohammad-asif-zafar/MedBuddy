package com.hathway.medbuddy.dashboard_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ui.Surface

@Composable
fun BloodGlucoseCard(
    average: Double = 7.4,
    trend: Double = 0.3,
    status: String = "In Range",
    onClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier.fillMaxWidth().height(320.dp).clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.MonitorHeart,
                        contentDescription = null,
                        tint = Color(0xFFFF2D55),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Blood Glucose",
                        color = Color(0xFFFF2D55),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                // LEFT SIDE
                Column {

                    Text(
                        text = average.toString(),
                        fontSize = 72.sp,
                        lineHeight = 72.sp,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFF0A24FF)
                    )

                    Text(
                        text = "mg/dL", fontSize = 24.sp, color = Color.Gray
                    )
                }

                // RIGHT SIDE
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Surface(
                        shape = RoundedCornerShape(50), color = Color(0xFFE8F7ED)
                    ) {

                        Row(
                            modifier = Modifier.padding(
                                horizontal = 12.dp, vertical = 6.dp
                            ), verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "🟢", fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = status,
                                color = Color(0xFF34C759),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Normal", color = Color.Gray, fontSize = 14.sp
                    )

                    Text(
                        text = "4.0 - 7.8", fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "↑ $trend from yesterday",
                color = Color(0xFF34C759),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))


            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Last Reading", fontSize = 26.sp, color = Color(0xFF0A24FF)
            )

            Text(
                text = "8.2 mg/dL • 4:59 PM -  BFF", color = Color.Gray, fontSize = 14.sp
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun BloodGlucoseCardPreview() {

    MaterialTheme {

        Box(
            modifier = Modifier.padding(16.dp)
        ) {

            BloodGlucoseCard(
                average = 7.4, trend = 0.3, status = "In Range"
            )
        }
    }
}
package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileHeaderCard(
    name: String,
    email: String,
    photoUrl: String,
    age: String,
    weight: String,
    bloodType: String,
    isLoading: Boolean = false,
    onEditPhotoClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEBEEE4)
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 44.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(120.dp)
                        )

                    } else if (photoUrl.isNotBlank()) {

                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Box(
                            modifier = Modifier.size(120.dp).clip(CircleShape)
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = name.firstOrNull()?.uppercase() ?: "A",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4F6B35)
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.size(38.dp).clickable { onEditPhotoClick() },
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF4F6B35)
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().offset(y = (-38).dp).padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            ProfileInfoCard(
                value = bloodType, title = "Blood Type", modifier = Modifier.weight(1f)
            )

            ProfileInfoCard(
                value = age, title = "Age", modifier = Modifier.weight(1f)
            )

            ProfileInfoCard(
                value = "$weight kg", title = "Weight", modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProfileInfoCard(
    value: String, title: String, modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF264D35)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = title, fontSize = 12.sp, color = Color(0xFF264D35)
            )
        }
    }
}
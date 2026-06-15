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

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(96.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else if (photoUrl.isNotBlank()) {

                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier.size(96.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                } else {

                    Box(
                        modifier = Modifier.size(96.dp).clip(CircleShape).background(
                            MaterialTheme.colorScheme.primaryContainer
                        ), contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = name.firstOrNull()?.uppercase() ?: stringResource(Res.string.profile_default_initial),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Surface(
                    modifier = Modifier.size(32.dp).clickable { onEditPhotoClick() },
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(Res.string.edit_photo),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(Res.string.edit_profile),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            onEditProfileClick()
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            if (email.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ProfileStatCard(
                    title = stringResource(Res.string.blood), value = bloodType, modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = stringResource(Res.string.age), value = age, modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = stringResource(Res.string.weight), value = weight, modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditProfileDialog(
    name: String,
    age: String,
    weight: String,
    bloodType: String,
    onDismiss: () -> Unit,
    onSave: (name: String, age: String, weight: String, bloodType: String) -> Unit
) {
    var editedName by remember { mutableStateOf(name) }
    var editedAge by remember { mutableStateOf(age) }
    var editedWeight by remember { mutableStateOf(weight) }
    var editedBloodType by remember { mutableStateOf(bloodType) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                // Header Area
                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Personal Metrics",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = stringResource(Res.string.edit_profile_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(
                            onClick = onDismiss, 
                            modifier = Modifier.background(
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), CircleShape
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(Res.string.close),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Styled Form Inputs
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Name Input
                    EditProfileInputField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = stringResource(Res.string.name),
                        icon = Icons.Default.Person
                    )

                    // Age Input
                    EditProfileInputField(
                        value = editedAge,
                        onValueChange = { editedAge = it },
                        label = stringResource(Res.string.age),
                        icon = Icons.Default.Numbers
                    )

                    // Weight Input
                    EditProfileInputField(
                        value = editedWeight,
                        onValueChange = { editedWeight = it },
                        label = stringResource(Res.string.weight_kg),
                        icon = Icons.Default.MonitorWeight
                    )

                    // Blood Type Input
                    EditProfileInputField(
                        value = editedBloodType,
                        onValueChange = { editedBloodType = it },
                        label = stringResource(Res.string.blood_type),
                        icon = Icons.Default.WaterDrop
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dialog Actions Row Section
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.discard),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                        PrimaryButton(
                            text = stringResource(Res.string.save_info), 
                            onClick = {
                                onSave(editedName, editedAge, editedWeight, editedBloodType)
                            }, 
                            modifier = Modifier.weight(1f).height(50.dp), 
                            enabled = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditProfileInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    )
}

package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.age
import medbuddy.composeapp.generated.resources.blood_type
import medbuddy.composeapp.generated.resources.discard
import medbuddy.composeapp.generated.resources.edit_profile_title
import medbuddy.composeapp.generated.resources.name
import medbuddy.composeapp.generated.resources.save_info
import medbuddy.composeapp.generated.resources.weight_kg
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

    val brandCream = Color(0xFFFEF9F0)
    val brandGreen = Color(0xFF1B5E20)
    val brandCreamDarker = Color(0xFFF4EFE6)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = brandCream,
            tonalElevation = 0.dp
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
                                color = Color.Gray
                            )
                            Text(
                                text = stringResource(Res.string.edit_profile_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        IconButton(
                            onClick = onDismiss, modifier = Modifier.background(
                                Color.Black.copy(alpha = 0.05f), CircleShape
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
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
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text(stringResource(Res.string.name)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = brandGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandGreen,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = brandGreen,
                            unfocusedLabelColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = brandCreamDarker
                        )
                    )

                    // Age Input
                    OutlinedTextField(
                        value = editedAge,
                        onValueChange = { editedAge = it },
                        label = { Text(stringResource(Res.string.age)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Numbers,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = brandGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandGreen,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = brandGreen,
                            unfocusedLabelColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = brandCreamDarker
                        )
                    )

                    // Weight Input
                    OutlinedTextField(
                        value = editedWeight,
                        onValueChange = { editedWeight = it },
                        label = { Text(stringResource(Res.string.weight_kg)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.MonitorWeight,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = brandGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandGreen,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = brandGreen,
                            unfocusedLabelColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = brandCreamDarker
                        )
                    )

                    // Blood Type Input
                    OutlinedTextField(
                        value = editedBloodType,
                        onValueChange = { editedBloodType = it },
                        label = { Text(stringResource(Res.string.blood_type)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = brandGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandGreen,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = brandGreen,
                            unfocusedLabelColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = brandCreamDarker
                        )
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
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                        ) {
                            Text(
                                text = stringResource(Res.string.discard),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                        PrimaryButton(
                            text = stringResource(
                                Res.string.save_info
                            ), onClick = {
                                onSave(
                                    editedName, editedAge, editedWeight, editedBloodType
                                )
                            }, modifier = Modifier.weight(1f).height(50.dp), enabled = true
                        )
                    }
                }
            }
        }
    }
}


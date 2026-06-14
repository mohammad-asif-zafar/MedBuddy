package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    name: String, age: String, weight: String, bloodType: String, onDismiss: () -> Unit, onSave: (
        name: String, age: String, weight: String, bloodType: String,
    ) -> Unit
) {

    var editedName by remember { mutableStateOf(name) }
    var editedAge by remember { mutableStateOf(age) }
    var editedWeight by remember { mutableStateOf(weight) }
    var editedBloodType by remember { mutableStateOf(bloodType) }

    AlertDialog(onDismissRequest = onDismiss, title = {
        Text(stringResource(Res.string.edit_profile_title))
    }, text = {
        Column {

            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                label = { Text(stringResource(Res.string.name)) })

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = editedAge,
                onValueChange = { editedAge = it },
                label = { Text(stringResource(Res.string.age)) })

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = editedWeight,
                onValueChange = { editedWeight = it },
                label = { Text(stringResource(Res.string.weight_kg)) })

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = editedBloodType,
                onValueChange = { editedBloodType = it },
                label = { Text(stringResource(Res.string.blood_type)) })
        }
    }, confirmButton = {

        PrimaryButton(
            text = stringResource(Res.string.save_info), onClick = {
                onSave(
                    editedName, editedAge, editedWeight, editedBloodType
                )
            }, modifier = Modifier, enabled = true
        )

    }, dismissButton = {
        TextButton(
            onClick = onDismiss,
            modifier = Modifier.height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(Res.string.discard), fontWeight = FontWeight.Bold)
        }
    })
}

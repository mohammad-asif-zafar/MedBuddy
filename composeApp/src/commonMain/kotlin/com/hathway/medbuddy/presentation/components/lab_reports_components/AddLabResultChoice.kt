package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.cancel
import medbuddy.composeapp.generated.resources.lab_action_import
import medbuddy.composeapp.generated.resources.lab_action_import_desc
import medbuddy.composeapp.generated.resources.lab_action_manual
import medbuddy.composeapp.generated.resources.lab_action_manual_desc
import medbuddy.composeapp.generated.resources.lab_action_ocr
import medbuddy.composeapp.generated.resources.lab_action_ocr_desc
import medbuddy.composeapp.generated.resources.lab_action_upload
import medbuddy.composeapp.generated.resources.lab_action_upload_desc
import medbuddy.composeapp.generated.resources.lab_add_desc
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddLabResultChoice(onManualClick: () -> Unit, onUploadClick: () -> Unit, onOCRClick: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(Res.string.lab_add_desc),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        ChoiceItem(
            title = stringResource(Res.string.lab_action_manual),
            desc = stringResource(Res.string.lab_action_manual_desc),
            icon = Icons.Outlined.Edit,
            onClick = onManualClick
        )
        ChoiceItem(
            title = stringResource(Res.string.lab_action_upload),
            desc = stringResource(Res.string.lab_action_upload_desc),
            icon = Icons.Outlined.FileUpload,
            onClick = onUploadClick
        )
        ChoiceItem(
            title = stringResource(Res.string.lab_action_import),
            desc = stringResource(Res.string.lab_action_import_desc),
            icon = Icons.Outlined.Devices,
            onClick = {}
        )
        ChoiceItem(
            title = stringResource(Res.string.lab_action_ocr),
            desc = stringResource(Res.string.lab_action_ocr_desc),
            icon = Icons.Outlined.DocumentScanner,
            onClick = onOCRClick
        )

        Spacer(Modifier.weight(1f))

        TextButton(onClick = onCancel) {
            Text(stringResource(Res.string.cancel))
        }
    }
}

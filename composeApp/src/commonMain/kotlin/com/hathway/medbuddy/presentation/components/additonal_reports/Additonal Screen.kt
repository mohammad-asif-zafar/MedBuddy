package com.hathway.medbuddy.presentation.components.additonal_reports


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack // KMP RTL handling support
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.action_back
import medbuddy.composeapp.generated.resources.list_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleListScreen(
    onBackClick: () -> Unit, modifier: Modifier = Modifier
) {
    // Generate a simple numerical array list for iteration
    val listItems = remember { (1..20).toList() }

    Scaffold(
        modifier = modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(text = stringResource(Res.string.list_title))
            }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        // AutoMirrored automatically flips for Right-to-Left languages
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(Res.string.action_back)
                    )
                }
            })
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            ), verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = listItems,
                key = { it } // Explicit unique primitive allocation tracing index key
            ) { itemNumber ->
                ListItem(headlineContent = {
                    Text("Medical Log Option #$itemNumber")
                }, supportingContent = {
                    Text("Tap to view details for this entry.")
                })
            }
        }
    }
}

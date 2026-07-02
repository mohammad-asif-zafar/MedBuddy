@file:Suppress("DEPRECATION")
package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.data.local.FakeGlucoseRepository
import com.hathway.medbuddy.presentation.components.all_add_reading_components.DateHeader
import com.hathway.medbuddy.presentation.components.all_add_reading_components.HistoryReadingItem
import com.hathway.medbuddy.presentation.components.all_add_reading_components.HistorySummaryCard
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.FullHistoryViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.recent_readings
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FullHistoryScreen(
    viewModel: FullHistoryViewModel,
    onBack: () -> Unit
) {
    val readings by viewModel.readings.collectAsState()
    val summary by viewModel.summary.collectAsState()

    // 1. Local state to track the active filter selection
    var selectedStatusFilter by remember { mutableStateOf("All") }
    val filters = remember { listOf("All", "Normal", "High", "Low") }

    // 2. Filter readings first, then group them chronologically
    val groupedReadings = remember(readings, selectedStatusFilter) {
        val filteredList = if (selectedStatusFilter == "All") {
            readings
        } else {
            readings.filter { it.status.equals(selectedStatusFilter, ignoreCase = true) }
        }
        filteredList.groupBy { it.date }
    }

    val today = remember { getNowLocalDateTime().date }
    val yesterday = remember { today.minus(1, DateTimeUnit.DAY) }

    BackHandler(enabled = true) {
        onBack()
    }

    Scaffold(
        topBar = {
            // Reused your project top bar component cleanly
            com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar(
                title = stringResource(Res.string.recent_readings),
                leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onLeftClick = onBack,
                titleColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dashboard Summary Card stays pinned at the top
            item(key = "summary_card") {
                HistorySummaryCard(summary)
            }

            // 3. Dynamic Material 3 Filter Chips Row Container
            item(key = "status_filters") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Filter by Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(filters) { filterText ->
                            val isSelected = selectedStatusFilter == filterText
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedStatusFilter = filterText },
                                label = { Text(text = filterText) },
                                leadingIcon = if (isSelected) {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Done,
                                            contentDescription = null,
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                } else null,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }

            // 4. Handle empty state gracefully if filtered results are blank
            if (groupedReadings.isEmpty()) {
                item(key = "empty_state") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No $selectedStatusFilter readings found.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Render your map listings based on filter state
                groupedReadings.forEach { (dateString, dateReadings) ->
                    item(key = "header_$dateString") {
                        DateHeader(dateString, today, yesterday)
                    }

                    items(
                        items = dateReadings,
                        key = { "${it.date}_${it.timePeriod}_${it.value}_${it.time}" }
                    ) { reading ->
                        HistoryReadingItem(reading)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ========================
// Previews Block
// ========================

@Preview
@Composable
fun FullHistoryScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        FullHistoryScreen(
            viewModel = FullHistoryViewModel(FakeGlucoseRepository()), onBack = {})
    }
}

@Preview
@Composable
fun FullHistoryScreenPreviewDark() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        FullHistoryScreen(
            viewModel = FullHistoryViewModel(FakeGlucoseRepository()), onBack = {})
    }
}
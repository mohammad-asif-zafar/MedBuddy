package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.components.history_components.HistoryReadingItem
import com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.FullHistoryViewModel
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun FullHistoryScreen(
    viewModel: FullHistoryViewModel,
    onBack: () -> Unit
) {
    val readings by viewModel.readings.collectAsState()

    Scaffold(
        topBar = {
            MedBuddyTopBar(
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(readings) { reading ->
                HistoryReadingItem(reading)
            }
        }
    }
}

// Safe mock data provider that bypasses network/database layers during preview compilation
class FakeGlucoseRepository : IGlucoseRepository {
     suspend fun getFullHistory(): List<RecentReading> {
        return listOf(
            RecentReading(date = "Today", timePeriod = "Before Breakfast", value = 98, time = "07:30 AM", notes = "Routine check"),
            RecentReading(date = "Yesterday", timePeriod = "After Dinner", value = 142, time = "09:15 PM", notes = "Had a small dessert"),
            RecentReading(date = "25 Jun", timePeriod = "Before Lunch", value = 105, time = "12:45 PM", notes = "")
        )
    }

    override suspend fun getAllRecords(): List<GlucoseRecord> {
        TODO("Not yet implemented")
    }

    override suspend fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String,
        mealType: String,
        notes: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String,
        mealType: String,
        notes: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun hasTimePeriodForDate(
        date: String,
        timePeriod: String
    ): Boolean {
        TODO("Not yet implemented")
    }
    // Implement other required IGlucoseRepository interface stubs with empty/default values below if needed
}



// 1. Standard Light Mode Layout Preview
@Preview
@Composable
fun FullHistoryScreenPreview() {
    val fakeRepository = FakeGlucoseRepository()
    val fakeViewModel = FullHistoryViewModel(repository = fakeRepository)

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        FullHistoryScreen(
            viewModel = fakeViewModel,
            onBack = {}
        )
    }
}

// 2. Dark Mode Variant Layout Preview
@Preview
@Composable
fun FullHistoryScreenDarkModePreview() {
    val fakeRepository = FakeGlucoseRepository()
    val fakeViewModel = FullHistoryViewModel(repository = fakeRepository)

    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        FullHistoryScreen(
            viewModel = fakeViewModel,
            onBack = {}
        )
    }
}


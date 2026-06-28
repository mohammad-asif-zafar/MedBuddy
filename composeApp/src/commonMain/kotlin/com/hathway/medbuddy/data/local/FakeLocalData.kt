package com.hathway.medbuddy.data.local

import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object FakeLocalData {
    val mockRecords = listOf(
        GlucoseRecord(
            date = "30 Jun 2026",
            beforeBreakfast = 95,
            afterBreakfast = 140,
            beforeLunch = 105,
            afterLunch = 155,
            beforeDinner = 110,
            afterDinner = 145,
            bedtime = 120,
            time = "10:30 PM",
            mealType = "BEDTIME",
            notes = "Feeling good",
            createdAt = 1000
        ),
        GlucoseRecord(
            date = "29 Jun 2026",
            beforeBreakfast = 110,
            afterBreakfast = 150,
            beforeLunch = 115,
            afterLunch = 160,
            beforeDinner = 120,
            afterDinner = 155,
            bedtime = 130,
            time = "11:00 PM",
            mealType = "BEDTIME",
            notes = "",
            createdAt = 900
        )
    )

    val mockDoctorInfo = DoctorInfo(
        doctorName = "Dr. Sumit Gulla",
        doctorType = "Specialist",
        speciality = "Endocrinologist",
        hospital = "Miracles Health",
        nextAppointment = "15 Jul 2026"
    )
}

// Safe mock data provider for compilation and previews
class FakeGlucoseRepository : IGlucoseRepository {
    override val recordsFlow: Flow<List<GlucoseRecord>> = flowOf(FakeLocalData.mockRecords)
    override suspend fun getAllRecords(): List<GlucoseRecord> = FakeLocalData.mockRecords
    
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
    ) {}

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
    ) {}

    override suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean = false
}

class FakeDoctorRepository : IDoctorRepository {
    override suspend fun getDoctorInfo(userId: String): DoctorInfo = FakeLocalData.mockDoctorInfo
    override suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {}
}

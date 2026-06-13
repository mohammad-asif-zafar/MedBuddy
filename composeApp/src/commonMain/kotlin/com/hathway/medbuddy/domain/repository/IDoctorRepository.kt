package com.hathway.medbuddy.domain.repository

import com.hathway.medbuddy.domain.model.DoctorInfo

interface IDoctorRepository {
    suspend fun getDoctorInfo(userId: String): DoctorInfo
    suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo)
}

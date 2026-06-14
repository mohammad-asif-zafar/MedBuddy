package com.hathway.medbuddy.data.repository

import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.repository.IDoctorRepository

class DoctorRepository : IDoctorRepository {
    override suspend fun getDoctorInfo(userId: String): DoctorInfo {
        // Mock implementation for iOS
        return DoctorInfo()
    }

    override suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {
        // Mock implementation for iOS
    }
}

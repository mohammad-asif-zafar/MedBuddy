package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.repository.IDoctorRepository

class SaveDoctorUseCase(
    private val repository: IDoctorRepository
) {
    suspend operator fun invoke(userId: String, doctorInfo: DoctorInfo) {
        repository.saveDoctorInfo(userId, doctorInfo)
    }
}

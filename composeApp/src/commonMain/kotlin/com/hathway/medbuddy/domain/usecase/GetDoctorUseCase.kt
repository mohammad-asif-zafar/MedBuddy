package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.domain.model.DoctorInfo

class GetDoctorUseCase {
    suspend operator fun invoke(userId: String): DoctorInfo {
        return FirebaseManager.getDoctorInfo(userId)
    }
}

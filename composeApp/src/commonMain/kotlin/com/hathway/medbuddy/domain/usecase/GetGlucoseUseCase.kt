package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository

class GetGlucoseUseCase(
    private val repository: IGlucoseRepository
) {
    operator fun invoke(records: List<GlucoseRecord>): List<GlucoseRecord> {
        return records
    }
}

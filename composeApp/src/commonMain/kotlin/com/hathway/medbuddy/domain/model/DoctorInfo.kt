package com.hathway.medbuddy.domain.model

data class DoctorInfo(
    val doctorName: String = "",
    val doctorType: String = "",
    val speciality: String = "",
    val hospital: String = "",
    val nextAppointment: String = ""
)

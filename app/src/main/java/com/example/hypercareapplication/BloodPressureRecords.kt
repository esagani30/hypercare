package com.example.hypercareapplication

import com.google.firebase.Timestamp

data class BloodPressureRecords(
    val systolic: Long,
    val diastolic: Long,
    val result: String,
    val recommendation: String,
    val timestamp: Timestamp? = null,
    val userId: String
)

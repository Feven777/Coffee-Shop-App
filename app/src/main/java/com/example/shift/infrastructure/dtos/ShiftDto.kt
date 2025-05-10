package com.example.shift.infrastructure.dtos

data class ShiftDto(
    val id: Int? = null, // Nullable for new shifts
    val shiftType: String,
    val date: String,
    val employeeId: String,
    val startTime: String,
    val endTime: String
)


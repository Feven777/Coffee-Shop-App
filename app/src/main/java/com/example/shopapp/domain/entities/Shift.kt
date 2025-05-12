package com.example.shopapp.domain.entities
data class Shift(
    val id: Int,
    val shiftType: String,
    val date: String,
    val employeeId: String,
    val startTime: String,
    val endTime: String
)

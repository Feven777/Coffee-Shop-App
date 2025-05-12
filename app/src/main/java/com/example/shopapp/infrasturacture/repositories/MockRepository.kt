package com.example.shopapp.infrasturacture.repositories
import com.example.shopapp.infrasturacture.dtos.ShiftDto

class MockShiftRepository {
    private val mockData = mutableListOf(
        ShiftDto(
            id = 1,
            shiftType = "Morning",
            date = "2025-05-08",
            employeeId = "6103a8d4f3c8b5f8b1d5f631",
            startTime = "08:00",
            endTime = "16:00"
        ),
        ShiftDto(
            id = 2,
            shiftType = "Evening",
            date = "2025-05-09",
            employeeId = "7103b9d5f4c9c6g9c2e6h732",
            startTime = "16:00",
            endTime = "23:00"
        )
    )

    fun getShifts(): List<ShiftDto> = mockData
    fun addShift(shift: ShiftDto) {
        mockData.add(shift.copy(id = mockData.size + 1)) // Simulates ID auto-generation
    }
    fun deleteShift(shiftId: Int) {
        mockData.removeIf { it.id == shiftId }
    }
}

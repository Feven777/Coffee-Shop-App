package com.example.shopapp.presentation.viewmodels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shopapp.infrasturacture.repositories.MockShiftRepository
import com.example.shopapp.infrasturacture.dtos.ShiftDto

class ShiftViewModel : ViewModel() {
    private val repository = MockShiftRepository()

    fun getShifts(): List<ShiftDto> {
        return repository.getShifts()
    }

    fun addShift(shiftType: String, date: String, employeeId: String, startTime: String, endTime: String) {
        repository.addShift(
            ShiftDto(
                shiftType = shiftType,
                date = date,
                employeeId = employeeId,
                startTime = startTime,
                endTime = endTime
            )
        )
    }

    fun deleteShift(shiftId: Int) {

        repository.deleteShift(shiftId)
    }

    fun updateShift(i: Int, shiftType: String, date: String, startTime: String, endTime: String) {

    }
}

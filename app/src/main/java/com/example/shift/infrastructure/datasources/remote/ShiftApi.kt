package com.example.shift.infrastructure.datasources.remote


import com.example.shift.infrastructure.dtos.ShiftDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShiftApi {
    @GET("/api/shifts") // ✅ Matches backend API structure
    suspend fun getAllShifts(): List<ShiftDto>

    @POST("/api/shifts")
    suspend fun addShift(@Body shift: ShiftDto)

    @DELETE("/api/shifts/{id}")
    suspend fun deleteShift(@Path("id") shiftId: Int)
}


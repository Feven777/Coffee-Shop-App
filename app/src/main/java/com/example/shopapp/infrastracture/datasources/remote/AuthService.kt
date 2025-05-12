package com.example.shopapp.infrastracture.datasources.remote

import com.example.shopapp.infrastracture.dtos.AuthResponse
import com.example.shopapp.infrastracture.dtos.LoginRequest
import com.example.shopapp.infrastracture.dtos.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("/api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}
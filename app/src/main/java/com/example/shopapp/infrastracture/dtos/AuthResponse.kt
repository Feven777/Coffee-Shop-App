package com.example.shopapp.infrastracture.dtos

data class AuthResponse(
    val token: String,
    val userId: String,
    val role: String
)
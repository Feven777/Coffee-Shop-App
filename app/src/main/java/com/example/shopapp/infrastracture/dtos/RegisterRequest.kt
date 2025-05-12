package com.example.shopapp.infrastracture.dtos


data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
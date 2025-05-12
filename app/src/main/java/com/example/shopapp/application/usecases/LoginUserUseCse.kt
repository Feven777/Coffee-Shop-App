package com.example.shopapp.application.usecases

import android.content.Context
import com.example.shopapp.infrastracture.dtos.AuthResponse
import com.example.shopapp.infrastracture.repositories.AuthRepository

class LoginUserUseCase(
    context: Context
) {
    private val authRepository = AuthRepository(context)

    suspend operator fun invoke(email: String, password: String): AuthResponse {
        return try {
            authRepository.loginUser(email, password)
        } catch (e: Exception) {
            throw Exception("Login failed: ${e.message}")
        }
    }
}
package com.example.shopapp.application.usecases

import android.content.Context
import com.example.shopapp.infrastracture.dtos.AuthResponse
import com.example.shopapp.infrastracture.repositories.AuthRepository

class RegisterUserUseCase(
    context: Context
) {
    private val authRepository = AuthRepository(context)

    /** Returns the AuthResponse containing the JWT token on success */
    suspend operator fun invoke(name: String, email: String, password: String): AuthResponse {
        return try {
            authRepository.registerUser(name, email, password)
        } catch (e: Exception) {
            throw Exception("Registration failed: ${e.message}")
        }
    }
}
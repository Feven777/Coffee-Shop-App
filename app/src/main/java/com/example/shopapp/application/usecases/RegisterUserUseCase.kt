package com.example.shopapp.application.usecases

import com.example.shopapp.domain.entities.User
import com.example.shopapp.infrastructure.repositories.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: User) {
        userRepository.register(user)
    }
}

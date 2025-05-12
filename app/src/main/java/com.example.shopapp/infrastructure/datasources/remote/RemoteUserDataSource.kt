package com.example.shopapp.infrastructure.datasources.remote

import com.example.shopapp.domain.entities.User

class RemoteUserDataSource {

    suspend fun login(user: User): User {
        if (user.email != "test@example.com" || user.password != "password") {
            throw Exception("Invalid credentials")
        }
        return user
    }

    suspend fun register(user: User): User {
        // Simulate registration logic (e.g., validate and return user)
        return user // Return the registered user
    }

    suspend fun resetPassword(email: String) {
        // Simulate password reset
    }
}
package com.example.shopapp.infrastructure.repositories

import com.example.shopapp.domain.entities.User

interface UserRepository {
    suspend fun login(user: User): User
    suspend fun register(user: User): User
    suspend fun resetPassword(email: String)
}
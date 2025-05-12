package com.example.shopapp.infrastructure.repositories

import com.example.shopapp.domain.entities.User

interface UserRepository {
    suspend fun register(user: User)
    suspend fun login(user: User)
    suspend fun forgotPassword(email: String)
}

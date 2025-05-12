package com.example.shopapp.infrastructure.repositories

import com.example.shopapp.infrastructure.datasources.remote.RemoteUserDataSource
import com.example.shopapp.domain.entities.User


class UserRepositoryImpl(private val remoteUserDataSource: RemoteUserDataSource) : UserRepository {

    override suspend fun login(user: User): User {
        return remoteUserDataSource.login(user) // Assume this returns User
    }

    override suspend fun register(user: User): User {
        return remoteUserDataSource.register(user)
    }

    override suspend fun resetPassword(email: String) {
        remoteUserDataSource.resetPassword(email)
    }
}
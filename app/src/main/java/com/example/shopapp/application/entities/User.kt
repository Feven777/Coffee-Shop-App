package com.example.shopapp.application.entities

data class User(
    val id: String,
    val username: String,
    val role: Role = Role.MANAGER
)

enum class Role {
    OWNER,
    MANAGER
}
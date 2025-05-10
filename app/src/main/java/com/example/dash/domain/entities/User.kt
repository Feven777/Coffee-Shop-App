package com.example.dash.domain.entities

data class User(
    val username: String,
    val role: Role
)

enum class Role {
    MANAGER,
    EMPLOYEE
}
package com.example.shopapp.domain.entities
data class User(
    val username: String,
    val role: Role,
    val email: String,
    val password: String,
    val fullName: String = ""
)

enum class Role {
    MANAGER,
    EMPLOYEE
}

package com.example.shopapp.domain.entities

data class User(
    val email: String,
    val password: String,
    val fullName: String = ""
)
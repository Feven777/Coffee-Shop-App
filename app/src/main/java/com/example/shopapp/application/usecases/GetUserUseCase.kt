package com.example.shopapp.application.usecases
import com.example.shopapp.domain.entities.Role
import com.example.shopapp.domain.entities.User

class GetUserUseCase {
    // Simulated user fetch (in a real app, this would interact with a repository)
    fun execute(): User {
        return User("John Doe", Role.MANAGER) // Hardcoded for demo
    }
}


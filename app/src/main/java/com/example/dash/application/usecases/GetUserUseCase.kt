package com.example.dash.application.usecases
import com.example.dash.domain.entities.Role
import com.example.dash.domain.entities.User

class GetUserUseCase {
    // Simulated user fetch (in a real app, this would interact with a repository)
    fun execute(): User {
        return User("John Doe", Role.MANAGER) // Hardcoded for demo
    }
}

package com.example.shopapp.application.usecases
import com. example. shopapp. infrastructure. repositories. UserRepository


class ForgotPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(email: String) {
        userRepository.sendPasswordResetEmail(email)
    }
}

private fun UserRepository.sendPasswordResetEmail(
    string: kotlin.String
) {
}

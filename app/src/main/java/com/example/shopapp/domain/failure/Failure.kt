package com.example.shopapp.domain.failure

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object DatabaseError : Failure()
    data class UnexpectedError(val message: String) : Failure()
}
package com.example.shopapp.presentation.viewmodels
import androidx.lifecycle.ViewModel
import com.example.shopapp.application.usecases.GetUserUseCase
import com.example.shopapp.application.usecases.LogoutUseCase
import com.example.shopapp.domain.entities.Role
import com.example.shopapp.domain.entities.User

class DashboardViewModel : ViewModel() {
    private val getUserUseCase = GetUserUseCase()
    private val logoutUseCase = LogoutUseCase()

    val user: User = getUserUseCase.execute()

    fun shouldShowInventoryTile(): Boolean {
        return user.role == Role.MANAGER
    }

    fun shouldShowSalesTile(): Boolean {
        return user.role == Role.MANAGER
    }

    fun shouldShowShiftsTile(): Boolean {
        return user.role == Role.MANAGER || user.role == Role.EMPLOYEE
    }

    fun logout() {
        logoutUseCase.execute()
        // In a real app, navigate to login screen
    }
}
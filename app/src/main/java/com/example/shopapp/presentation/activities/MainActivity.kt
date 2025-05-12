package com.example.shopapp.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.infrastracture.datasources.local.SettingsDataStore
import com.example.shopapp.presentation.theme.CoffeeTheme
import com.example.shopapp.presentation.ui.LoginScreen
import com.example.shopapp.presentation.ui.RegisterScreen
import com.example.shopapp.presentation.ui.SalesScreen
import com.example.shopapp.presentation.viewmodels.AuthViewModel
import com.example.shopapp.presentation.viewmodels.SalesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.example.shopapp.presentation.activities.DashboardScreen
import com.example.shopapp.presentation.screens.InventoryScreen
import com.example.shopapp.presentation.screens.ShiftsScreen

class MainActivity : ComponentActivity() {
    private val authViewModel by lazy { AuthViewModel(application) }
    private val salesViewModel by lazy { SalesViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            SettingsDataStore.clearToken(applicationContext)
            SettingsDataStore.clearUserRole(applicationContext)
            Log.d("MainActivity", "❌ Token & Role cleared on restart")
        }

        setContent {
            CoffeeTheme {
                val navController = rememberNavController()
                var startDest by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    try {
                        SettingsDataStore.userRoleFlow(applicationContext)
                            .combine(SettingsDataStore.tokenFlow(applicationContext)) { role, token ->
                                role to token
                            }
                            .collectLatest { (role, token) ->
                                Log.d("MainActivity", "Fetched role: $role, token: $token") // ✅ Debugging log

                                startDest = when {
                                    token.isNullOrEmpty() -> "register" // ✅ If token is missing, go to register
                                    role?.lowercase() == "admin" -> "dashboard" // ✅ Admin goes to dashboard
                                    else -> "sales" // ✅ Employees go to sales
                                }
                            }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error fetching token/role: ${e.message}")
                        startDest = "register" // ✅ Ensure app doesn't get stuck in loading
                    }
                }

                if (startDest == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    NavHost(navController = navController, startDestination = startDest!!) {
                        composable("register") {
                            RegisterScreen(
                                onRegister = { name, email, pwd ->
                                    authViewModel.registerUser(name, email, pwd) {
                                        navController.navigate("login") {
                                            popUpTo("register") { inclusive = true }
                                        }
                                    }
                                },
                                isLoading = authViewModel.isLoading.value,
                                errorMessage = authViewModel.errorMessage.value,
                                onLogin = { navController.navigate("login") }
                            )
                        }

                        composable("login") {
                            LoginScreen(
                                onLogin = { email, pwd ->
                                    authViewModel.loginUser(email, pwd) {
                                        navController.navigate("dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                },
                                isLoading = authViewModel.isLoading.value,
                                errorMessage = authViewModel.errorMessage.value
                            )
                        }

                        composable("dashboard") {
                            DashboardScreen(navController)
                        }

                        composable("inventory") {
                            InventoryScreen()
                        }

                        composable("shifts") {
                            ShiftsScreen()
                        }

                        composable("sales") {
                            SalesScreen(viewModel = salesViewModel, onNavigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }

    // Function to determine the starting destination based on user role
    private fun getStartDestination(token: String, role: String?): String {
        return if (token.isNotEmpty()) {
            if (role?.lowercase() == "admin") "dashboard" else "sales" // ✅ Explicitly check for "admin"
        } else {
            "register"
        }
    }

}
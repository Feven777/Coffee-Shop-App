package com.example.shopapp.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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

class MainActivity : ComponentActivity() {
    private val authViewModel by lazy { AuthViewModel(application) }  // ✅ Ensure ViewModel is accessible
    private val salesViewModel by lazy { SalesViewModel(application) }  // ✅ Provide Sales ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Clear authentication data when app starts
        lifecycleScope.launch {  // ✅ Use lifecycleScope instead of viewModelScope
            SettingsDataStore.clearToken(applicationContext)
            SettingsDataStore.clearUserRole(applicationContext)
            Log.d("MainActivity", "❌ Token & Role cleared on restart")
        }



        setContent {
            CoffeeTheme {
                val navController = rememberNavController()

                // ✅ Store Token & Role for Navigation Handling
                var startDest by remember { mutableStateOf<String?>(null) }

                // ✅ Fetch Token & Role Simultaneously
                LaunchedEffect(Unit) {
                    try {
                        SettingsDataStore.userRoleFlow(this@MainActivity)
                            .combine(SettingsDataStore.tokenFlow(this@MainActivity)) { role, token ->
                                getStartDestination(token ?: "", role)
                            }
                            .collectLatest { startDest = it }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error fetching token/role: ${e.message}")
                        startDest = "register"
                    }
                }

                // ✅ Show Loading While Fetching Data
                if (startDest == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                } else {
                    NavHost(
                        navController = navController,
                        startDestination = startDest!!
                    ) {

                            composable("register") {
                                RegisterScreen(
                                    onRegister = { name, email, pwd ->
                                        authViewModel.registerUser(name, email, pwd) {
                                            navController.navigate("login") {
                                                popUpTo("register") { inclusive = true }
                                            }
                                        }
                                    },
                                    isLoading = authViewModel.isLoading.value,  // ✅ Pass `isLoading`
                                    errorMessage = authViewModel.errorMessage.value,  // ✅ Pass `errorMessage`
                                    onLogin = { navController.navigate("login") }  // ✅ Fix missing comma!
                                )
                            }

                        composable("login") {
                            LoginScreen(
                                onLogin = { email, pwd ->
                                    authViewModel.loginUser(email, pwd) {
                                        navController.navigate("sales") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                },
                                isLoading = authViewModel.isLoading.value,  // ✅ Pass `isLoading`
                                errorMessage = authViewModel.errorMessage.value  // ✅ Pass `errorMessage`
                            )
                        }

                        composable("sales") {
                            SalesScreen(
                                viewModel = salesViewModel,  // ✅ Pass ViewModel
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // ✅ Placeholder for Admin Dashboard until merge
                        composable("admin_dashboard") {
                            AdminDashboardPlaceholder()
                        }
                    }
                }
            }
        }
    }

    // ✅ Modular Function for Role-Based Navigation
    private fun getStartDestination(token: String, role: String?): String {
        return "register"  // ✅ Forces login on app restart
    }


    // ✅ Temporary Placeholder UI for Admin Dashboard Until Merge
    @Composable
     fun AdminDashboardPlaceholder() { Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) { Text("Admin Dashboard Coming Soon...",
        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
    }




    }

}
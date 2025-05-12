package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.presentation.theme.ShopAppTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.example.shopapp.presentation.viewmodels.StyledBackground
import com.example.shopapp.presentation.viewmodels.LoginScreen
import com. example. shopapp. presentation. viewmodels. SignUpScreen
import androidx.compose.material3.*
import com. example. shopapp. presentation. viewmodels. ForgotPasswordScreen
import com. example. shopapp. presentation. viewmodels. HomeScreen
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import android. util. Log
import androidx. compose. ui. graphics. Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContent {
            Log.d("MainActivity", "setContent called")
            val navController = rememberNavController()

            ShopAppTheme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                      // Changed this for debugging
                ) {
                    MainScreen(navController)
                }
            }
        }
    }
}

// Remove the duplicate StyledBackground composable from MainActivity
// ... existing code ...

@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("login") {
            StyledBackground {  // Use the one from StyledBackground.kt
                LoginScreen(
                    onLoginClick = { email, password ->
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate("signup")
                    },
                    onForgotPasswordClick = {
                        navController.navigate("forgot_password")
                    }
                )
            }
        }

        composable("signup") {
            StyledBackground {
                SignUpScreen(
                    onSignUpClick = { email, password, fullName ->
                        // Implement signup logic
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }

        composable("forgot_password") {
            StyledBackground {
                ForgotPasswordScreen(
                    onNavigateBack = { navController.navigateUp() },
                    onResetPassword = { email ->
                        // Implement password reset logic
                        navController.navigate("login")
                    }
                )
            }
        }

        composable("home") {
            StyledBackground {
                HomeScreen(
                    onLogoutClick = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onDeleteAccountClick = {
                        // Implement delete account logic
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
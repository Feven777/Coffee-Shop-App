package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.presentation.theme.Theme
import com.example.shopapp.presentation.viewmodels.ForgotPasswordScreen
import com.example.shopapp.presentation.viewmodels.LoginScreen
import com.example.shopapp.presentation.viewmodels.RegistrationScreen
import com.example.shopapp.presentation.viewmodels.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                ShopAppNavigation()
            }
        }
    }
}

@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}
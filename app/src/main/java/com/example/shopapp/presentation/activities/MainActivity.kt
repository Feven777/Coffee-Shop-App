package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.shopapp.presentation.activities.SalesScreen
import com.example.coffeeshopapp.presentation.viewmodel.SalesViewModel
import com.example.shopapp.presentation.theme.CoffeeTheme

class MainActivity : ComponentActivity() {
    private val salesViewModel: SalesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent(
                viewModel = salesViewModel,
                onNavigateBack = { finish() }
            )
        }
    }
}

@Composable
fun AppContent(
    viewModel: SalesViewModel,
    onNavigateBack: () -> Unit
) {
    CoffeeTheme {
        Surface {
            SalesScreen(
                viewModel = viewModel,
                onNavigateBack = onNavigateBack
            )
        }
    }
}

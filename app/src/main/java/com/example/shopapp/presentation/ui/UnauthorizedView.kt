package com.example.shopapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shown when the user’s JWT role is not authorized to view the Sales screen.
 *
 * @param onGoToLogin Optional callback to navigate back to the login screen.
 */
@Composable
fun UnauthorizedView(
    onGoToLogin: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚫 Unauthorized",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You do not have permission to view this page.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        onGoToLogin?.let { navigate ->
            Button(onClick = navigate) {
                Text("Go to Login")
            }
        }
    }
}

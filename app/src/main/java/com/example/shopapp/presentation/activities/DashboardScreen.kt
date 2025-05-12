
package com.example.shopapp.presentation.activities
import androidx. compose. animation. core. animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopapp.presentation.theme.DashTheme
import androidx. compose. ui. graphics. painter. Painter
import androidx. compose. animation. core. tween
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopApp.R
import com.example.shopapp.presentation.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val authViewModel: AuthViewModel = viewModel()

    DashTheme {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "☕ Coffee Abyssinia",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8D6E63),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                },
                containerColor = Color.Transparent
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedStatCard("📦 Number of stock items", "24")
                    AnimatedStatCard("💰 Today's Revenue", "$1,245.50")
                    AnimatedStatCard("🕒 Next Shift", "Today, 2:00 PM")

                    Spacer(Modifier.height(20.dp))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AnimatedFooterButton(
                            icon = painterResource(R.drawable.ic_inventory),
                            label = "Inventory",
                            onClick = { navController.navigate("inventory") },
                            modifier = Modifier.weight(1f)
                        )
                        AnimatedFooterButton(
                            icon = painterResource(R.drawable.ic_shift),
                            label = "Shifts",
                            onClick = { navController.navigate("shifts") },
                            modifier = Modifier.weight(1f)
                        )
                        AnimatedFooterButton(
                            icon = painterResource(R.drawable.ic_sales),
                            label = "Sales",
                            onClick = { navController.navigate("sales") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Button(
                        onClick = {
                            authViewModel.logoutUser {
                                navController.navigate("login") // Navigate to login screen after logout
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8D6E63),
                            contentColor = Color.White // White text
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(text = "Logout", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                }
            }
        }
    }
}

@Composable
fun AnimatedStatCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 24.sp, color = Color(0xFF8D6E63))
        }
    }
}

@Composable
fun AnimatedFooterButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null // Make icon optional
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )

    Column(
        modifier = modifier
            .padding(8.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Image(painter = it, contentDescription = label, modifier = Modifier.size(48.dp))
        }
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

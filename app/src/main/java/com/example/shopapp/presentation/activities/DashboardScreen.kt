package com.example.shopapp.presentation.activities

import androidx.compose.foundation.Image
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import com.example.shopApp.R
import com.example.shopapp.presentation.theme.DashTheme
import com.example.shopapp.presentation.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = viewModel()
) {
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
                            painterResource(R.drawable.ic_inventory),
                            "Inventory",
                            { navController.navigate("inventory") },
                            Modifier.weight(1f)
                        )
                        AnimatedFooterButton(
                            painterResource(R.drawable.ic_shift),
                            "Shifts",
                            { navController.navigate("shifts") },
                            Modifier.weight(1f)
                        )
                        AnimatedFooterButton(
                            painterResource(R.drawable.ic_sales),
                            "Sales",
                            { navController.navigate("sales") },
                            Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedStatCard(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .height(100.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D4C41))
        }
    }
}

@Composable
fun AnimatedFooterButton(
    icon: Painter,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        if (pressed) 1.1f else 1f,
        tween(200)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(6.dp)
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
            }
            .height(100.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFF8D6E63),
            shadowElevation = if (pressed) 8.dp else 2.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(icon, label, tint = Color.White, modifier = Modifier.size(28.dp))
                Spacer(Modifier.height(6.dp))
                Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

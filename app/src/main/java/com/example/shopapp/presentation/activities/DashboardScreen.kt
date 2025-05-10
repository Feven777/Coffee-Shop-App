package com.example.shopapp.presentation.activities
import com.example.shopApp.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopapp.presentation.theme.DashTheme
import com.example.shopapp.presentation.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    DashTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image covering full screen
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Ensures it fills the screen properly
                modifier = Modifier.fillMaxSize()
                    .height(100.dp) // Restrict height to place it at bottom
                    .align(Alignment.BottomCenter)
                    .width(100.dp)
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "☕ Coffee Abyssinia",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8D6E63),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent // Ensures scaffold doesn't cover background
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SummaryButton("Number of stock items", "24")
                    SummaryButton("Today's Revenue", "$1,245.50")
                    SummaryButton("Next Shift", "Today, 2:00 PM")

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FooterButton("Inventory", Modifier.weight(1f))
                        FooterButton("Shifts", Modifier.weight(1f))
                        FooterButton("Sales", Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryButton(title: String, value: String) {
    Button(
        onClick = { /* Handle click */ },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7CCC8)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(90.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF757575))
        }
    }
}

@Composable
fun FooterButton(label: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Handle click */ },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63)),
        modifier = modifier
            .padding(2.dp)
            .height(60.dp)
    ) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
    }
}
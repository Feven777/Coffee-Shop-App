package com.example.shopapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.application.entities.SalesData
import com.example.shopapp.application.entities.TopSellingItem
import com.example.shopapp.presentation.viewmodels.SalesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    viewModel: SalesViewModel,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchSalesData()
    }

    val salesData = viewModel.salesData.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Coffee Abyssinya ", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage.isNotEmpty() -> Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                salesData != null -> SalesContent(data = salesData)
                else -> Text("No sales data available.")
            }
        }
    }
}


@Composable
fun SalesContent(data: SalesData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Sales Overview",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RevenueCard("Daily Revenue", data.dailyRevenue, data.dailyRevenueTrend, Modifier.weight(1f))
                RevenueCard("Weekly Revenue", data.weeklyRevenue, data.weeklyRevenueTrend, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RevenueCard("Monthly Revenue", data.monthlyRevenue, data.monthlyRevenueTrend, Modifier.weight(1f))
                RevenueCard("Average Order Value", data.averageOrderValue, data.averageOrderValueTrend, Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 "Top Selling Items" section remains visually consistent
        Text(text = "Top Selling Items", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.topSellingItems) { item ->
                TopSellingItemRow(item = item)
                HorizontalDivider()
            }
        }
    }
}

// 📊 Revenue Card styled exactly like the image
@Composable
fun RevenueCard(label: String, amount: Double, trend: Double, modifier: Modifier) {
    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = label, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${amount} Br",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${(trend * 100).toInt()}% " + if (trend >= 0) "increase" else "decrease",
                style = MaterialTheme.typography.bodySmall,
                color = if (trend >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    }
}


@Composable
fun TopSellingItemRow(item: TopSellingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.productName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(text = "Units Sold: ${item.unitsSold}", style = MaterialTheme.typography.bodySmall)
        }
        Column {
            Text(text = "Revenue: ${item.revenue} Br", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(
                text = "${(item.trend * 100).toInt()}% " + if (item.trend >= 0) "increase" else "decrease",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = if (item.trend >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    }
}
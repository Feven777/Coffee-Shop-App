package com.example.shopapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.domain.entities.SalesData
import com.example.shopapp.domain.entities.TopSellingItem
import androidx. compose. ui. text. font. FontWeight
import androidx. compose. ui. Alignment
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    salesData: SalesData,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Coffee Abyssinya", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Sales Overview", style = MaterialTheme.typography.headlineSmall)

            // Revenue cards
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RevenueCard("Daily Revenue", salesData.dailyRevenue, salesData.dailyRevenueTrend, Modifier.weight(1f))
                RevenueCard("Weekly Revenue", salesData.weeklyRevenue, salesData.weeklyRevenueTrend, Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RevenueCard("Monthly Revenue", salesData.monthlyRevenue, salesData.monthlyRevenueTrend, Modifier.weight(1f))
                RevenueCard("Average Order Value", salesData.averageOrderValue, salesData.averageOrderValueTrend, Modifier.weight(1f))
            }

            Text("Top Selling Items", style = MaterialTheme.typography.headlineSmall)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(salesData.topSellingItems) { item ->
                    TopSellingItemRow(item)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun RevenueCard(
    label: String,
    amount: Double,
    trend: Double,
    modifier: Modifier
) = Card(
    modifier = modifier.height(100.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(6.dp)
) {
    Column(
        Modifier.padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text("${amount} Br", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(
            "${(trend * 100).toInt()}% ${if (trend >= 0) "increase" else "decrease"}",
            color = if (trend >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TopSellingItemRow(item: TopSellingItem) = Row(
    Modifier.fillMaxWidth().padding(8.dp),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Column {
        Text(item.productName, fontWeight = FontWeight.Bold)
        Text("Units Sold: ${item.unitsSold}", style = MaterialTheme.typography.bodySmall)
    }
    Column(horizontalAlignment = Alignment.End) {
        Text("Revenue: ${item.revenue} Br", fontWeight = FontWeight.Bold)
        Text(
            "${(item.trend*100).toInt()}% ${if (item.trend>=0) "increase" else "decrease"}",
            color = if (item.trend>=0) Color(0xFF4CAF50) else Color(0xFFF44336),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

package com.example.shift.presentation.activities
import com.example.shift.presentation.theme.CoffeeTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shift.R
import com.example.shift.presentation.theme.CoffeeBrownDark
import com.example.shift.presentation.viewmodels.ShiftViewModel

@Composable
fun ShiftScreen(viewModel: ShiftViewModel = ShiftViewModel()) {
    val shifts = viewModel.getShifts() // Fetch mock shifts
    var showShifts by remember { mutableStateOf(false) }
    var showAddShift by remember { mutableStateOf(false) }
    var showUpdateShift by remember { mutableStateOf(false) }
    var showDeleteShift by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // ✅ Corrected Background Image Scaling
        Image(
            painter = painterResource(id = R.drawable.coffee_background),
            contentDescription = "Coffee Beans Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // ✅ Ensures buttons are properly stacked!
        ) {
        // ✅ Background Image (Coffee Beans)
            Text(
                text = "Shift Management",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ✅ View Shifts Button (Opens LazyColumn)
            SquareButton(text = if (showShifts) "Hide Shifts" else "View Shifts", iconRes = R.drawable.ic_view) {
                showShifts = !showShifts
            }
            if (showShifts) {
                LazyColumn {
                    items(items = shifts){ shift ->
                        Text(text = "${shift.shiftType} - ${shift.date} - ${shift.startTime} - ${shift.endTime}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // ✅ Add Shift Button (Opens Form)
            SquareButton(text = if (showAddShift) "Hide Add Shift" else "Add Shift", iconRes = R.drawable.ic_add) {
                showAddShift = !showAddShift
            }
            if (showAddShift) { ShiftForm(viewModel, "add") }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Update Shift Button (Opens Form)
            SquareButton(text = if (showUpdateShift) "Hide Update Shift" else "Update Shift", iconRes = R.drawable.ic_edit) {
                showUpdateShift = !showUpdateShift
            }
            if (showUpdateShift) { ShiftForm(viewModel, "update") }

            Spacer(modifier = Modifier.height(16.dp))


            // ✅ Delete Shift Button (Opens Input)
            SquareButton(text = if (showDeleteShift) "Hide Delete Shift" else "Delete Shift", iconRes = R.drawable.ic_delete) {
                showDeleteShift = !showDeleteShift
            }
            if (showDeleteShift) { ShiftForm(viewModel, "delete") }
        }
    }
}
// ✅ Square Button with Icon
@Composable
fun SquareButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = iconRes), contentDescription = "$text Icon", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White, fontSize = 18.sp)
        }
    }
}
// ✅ Shift Form for Add, Update, Delete Actions
@Composable
fun ShiftForm(viewModel: ShiftViewModel, actionType: String) {
    var shiftId by remember { mutableStateOf("") }
    var shiftType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var employeeId by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    Column {
        if (actionType != "delete") {
            TextField(value = shiftType, onValueChange = { shiftType = it }, label = { Text("Shift Type") })
            TextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
            TextField(value = employeeId, onValueChange = { employeeId = it }, label = { Text("Employee ID") })
            TextField(value = startTime, onValueChange = { startTime = it }, label = { Text("Start Time") })
            TextField(value = endTime, onValueChange = { endTime = it }, label = { Text("End Time") })
        } else {
            TextField(value = shiftId, onValueChange = { shiftId = it }, label = { Text("Shift ID to Delete") })
        }
        Button(onClick = {
            when (actionType) {
                "add" -> viewModel.addShift(shiftType, date, employeeId, startTime, endTime)
                "update" -> viewModel.updateShift(shiftId.toIntOrNull() ?: -1, shiftType, date, startTime, endTime)
                "delete" -> viewModel.deleteShift(shiftId.toIntOrNull() ?: -1)
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Confirm $actionType Shift")
        }
    }
}



package com.example.a5046protoytpe


import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.Instant
import java.util.Calendar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(0)
@Composable

fun HomePage(navController: NavController, weatherViewModel: WeatherViewModel) {
    /*Date Picker Section*/
    val calendar = Calendar.getInstance()
    calendar.set(2024, 0, 1) // month (0) is January
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }
    val temperature = weatherViewModel.temperature.value
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
    val todayDate = LocalDate.now().format(dateFormatter)
    Column(modifier = Modifier.padding(16.dp)) {
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedDate = datePickerState.selectedDateMillis!!
                        showDatePicker = false
                        val selectedLocalDate = Instant.ofEpochMilli(selectedDate).atZone(ZoneId.systemDefault()).toLocalDate()
                        val today = LocalDate.now()
                        if (selectedLocalDate.isEqual(today)) {
                            navController.navigate("StartExercise")
                        }
                        else {
                            val dateString = selectedLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                            navController.navigate("Report/$dateString")
                        }

                    }) {Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) //end of dialog
            { //still column scope
                DatePicker(
                    state = datePickerState
                )
            }
        }// end of if

    }

    /*UI of this page*/
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp) // Adjust padding
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.pf),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp)) // Adjust the spacing
            Column {
                Text(
                    text = "Hello!",
                )
                Text(
                    text = todayDate,
                    color = Color.Gray,
                )
                Text(text = "Current Temperature: $temperature")
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(30.dp)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ){
                Text(
                    text = "Select a date to check previous exercise record"
                )
                Spacer(modifier = Modifier.width(16.dp)) // Adjust the spacing
                Text(
                    text = "To start exercise, please select the date of today"
                )
                Spacer(modifier = Modifier.width(20.dp)) // Adjust the spacing
                Button(
                    onClick = {
                        showDatePicker = true
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFfe703f)), // Set the button color
                    shape = CircleShape,
                    modifier = Modifier.size(100.dp)
                    //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
                ) {
                    Text(text = "Start", color = Color.White)
                }
            }
        }

    }
}

/*@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    val navController = rememberNavController()
    HomePage(navController = navController)
}*/


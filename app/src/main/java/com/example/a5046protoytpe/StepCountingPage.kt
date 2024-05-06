package com.example.a5046protoytpe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



@Composable
fun StepCountingPage(sensorManager: SensorManager, navController: NavController) {
    val context = LocalContext.current
    val exerciseDatabase = remember { ExerciseDatabase.getDatabase(context) }
    val dao = exerciseDatabase.exerciseDAO()

    // State for counting steps
    var steps by remember { mutableStateOf(0) }
    var distance by remember { mutableStateOf(0.0) }
    val stepLengthInMeters = 0.762 // Average step length in meters

    val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    steps++
                    distance = steps * stepLengthInMeters / 1000 // Calculate the estimate walking distance and convert meters to kilometers
                }
            }
        }
    }

    // Register the sensor listener
    DisposableEffect(true) {
        sensorManager.registerListener(sensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    //Timer
    val startTime = remember { System.currentTimeMillis() }
    var currentTime by remember { mutableStateOf(0L) }
    var timerRunning by remember { mutableStateOf(true) } // State to control the timer running

    LaunchedEffect(key1 = true) {
        while (timerRunning) {
            currentTime = System.currentTimeMillis() - startTime
            delay(1000) // Update the timer every second
        }
    }

    val hours = (currentTime / 3600000).toInt()
    val minutes = ((currentTime % 3600000) / 60000).toInt()
    val seconds = ((currentTime % 60000) / 1000).toInt()
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(15.dp)
                .background(
                    color = Color(0xFFFDEEE9),
                    shape = RoundedCornerShape(30.dp)
                )
                .fillMaxWidth()

        ) {
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp
                )
            )
            Text(
                text = "Distance: ${"%.2f".format(distance)} KM",
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = "Speed: ${calculateSpeed(distance, currentTime)} KM/H",
                color = Color.Gray,
                fontSize = 18.sp
            )
            Button(
                onClick = {
                    steps++
                    distance = steps * stepLengthInMeters / 1000 // Update distance as if a step was taken
                },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFfe703f))

            ) {
                Text("Simulate Step")
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ){
            Text(
                text = "Click the button below to stop exercise",
                color = Color.Gray,
                fontSize = 18.sp
            )
            Button(
                onClick = {
                    navController.navigate("Report")
                    timerRunning = false
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val timeInSeconds = (currentTime / 1000).toInt() // Convert time to seconds
                    val newExercise = Exercise(distance = distance.toFloat(), time = timeInSeconds, date = currentDate)
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insertExercise(newExercise)
                    }
                },
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(width = 150.dp, height = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFfe703f) // Replace with your color
                )
            ) {
                Text(
                    text = "Stop",
                    color = Color.White,
                    fontSize = 30.sp
                )

            }

        }


    }
}

fun calculateSpeed(distance: Double, timeMillis: Long): Double {
    val hours = timeMillis / 3600000.0
    return if (hours > 0) {
        String.format("%.2f", distance / hours).toDouble()
    } else {
        0.0
    }
}
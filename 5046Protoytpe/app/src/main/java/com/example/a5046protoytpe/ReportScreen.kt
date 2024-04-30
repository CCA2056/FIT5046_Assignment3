package com.example.a5046prototype

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a5046protoytpe.ExerciseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(exerciseViewModel: ExerciseViewModel) {
    val exercises by exerciseViewModel.allExercise.observeAsState(emptyList())
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMMM")
    val formattedDate = today.format(formatter)

    // Filter today's exercises, sum distance and time
    val todayExercises = exercises.filter {
        LocalDate.parse(it.date).isEqual(today)
    }
    val totalDistanceToday = todayExercises.fold(0f) { sum, exercise -> sum + exercise.distance }
    val totalTimeToday = todayExercises.fold(0) { sum, exercise -> sum + exercise.time }


    // Calculate calories based on distance (Example: 100 calories per mile)
    val caloriesBurned = (totalDistanceToday * 0.621371 * 100).roundToInt() // Convert km to miles then calculate calories

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formattedDate,
            //style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LineChart() // Placeholder for your line chart
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "$caloriesBurned",
            //style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MetricDisplay("Calories", "$caloriesBurned")
            MetricDisplay("Time", formatTime(totalTimeToday))
            MetricDisplay("Kilometers", "${"%.2f".format(totalDistanceToday)}")
        }
    }
}

@Composable
fun MetricDisplay(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value)
        Text(text = label)
    }
}

@Composable
fun LineChart() {
    // Placeholder for your line chart component
    Box(modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()
        .background(Color.Blue), contentAlignment = Alignment.Center) {
        Text("Graph", color = Color.White)
    }
}

fun formatTime(totalDurationInSeconds: Int): String {
    val hours = totalDurationInSeconds / 3600
    val minutes = (totalDurationInSeconds % 3600) / 60
    val seconds = totalDurationInSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
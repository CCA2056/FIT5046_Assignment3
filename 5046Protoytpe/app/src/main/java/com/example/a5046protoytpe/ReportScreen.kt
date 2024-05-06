@file:Suppress("NAME_SHADOWING")

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(exerciseViewModel: ExerciseViewModel, navController: NavController) {
    val exercises by exerciseViewModel.allExercise.observeAsState(emptyList())
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMMM")
    val formattedDate = today.format(formatter)

    // Filter today's exercises, sum distance and time
    val todayExercises = exercises.filter {
        LocalDate.parse(it.date).isEqual(today)
    }.takeLast(5)

    val labels = todayExercises.mapIndexed { index, _ -> "Exercise ${index + 1}" }
    val distances = todayExercises.map { it.distance }

    val barEntries = distances.mapIndexed { index, distance ->
        BarEntry(index.toFloat(), distance)
    }
    val barDataSet = BarDataSet(barEntries, "Exercise in today")
    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val barData = BarData(barDataSet).apply {
        barWidth = 0.9f // Adjust bar width here
    }

    val totalDistanceToday = todayExercises.fold(0f) { sum, exercise -> sum + exercise.distance }
    val totalTimeToday = todayExercises.fold(0) { sum, exercise -> sum + exercise.time }
    val caloriesBurned = ((totalTimeToday / 60) * 8.4).roundToInt() // Calories burned per minute calculation

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = formattedDate,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricDisplay("Calories", "$caloriesBurned")
                MetricDisplay("Time", formatTime(totalTimeToday))
                MetricDisplay("Kilometers", "${"%.2f".format(totalDistanceToday)}")
            }
            Spacer(modifier = Modifier.height(40.dp))
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp), // Set the height for the chart
                factory = { context ->
                    BarChart(context).apply {
                        data = barData
                        description.isEnabled = false
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            isGranularityEnabled = true
                            valueFormatter = IndexAxisValueFormatter(labels)
                            setDrawLabels(true)
                            setDrawGridLines(false)
                        }
                        axisLeft.apply {
                            setDrawGridLines(false)
                            axisMinimum = 0f
                        }
                        axisRight.apply {
                            setDrawGridLines(false)
                            axisMinimum = 0f
                        }
                        setFitBars(true)
                        animateY(500)
                    }
                },
                update = { chart ->
                    chart.apply {
                        data = barData
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            isGranularityEnabled = true
                            valueFormatter = IndexAxisValueFormatter(labels)
                            setDrawLabels(true)
                            setDrawGridLines(false)
                        }
                        invalidate()
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(todayExercises.size) { index ->
            val exercise = todayExercises[index]
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Exercise ${index + 1}")
                    Text("Distance: ${exercise.distance} KM")
                    Text("Time: ${formatTime(exercise.time)}")
                    val calories = (exercise.time / 60 * 8.4).roundToInt() // Example calculation
                    Text("Calories Burned: $calories")
                }
            }
        }

        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { navController.navigate("StartExercise") },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFfe703f))
                ) {
                    Text("Click to Start New Exercise")
                }
            }
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


fun formatTime(totalDurationInSeconds: Int): String {
    val hours = totalDurationInSeconds / 3600
    val minutes = (totalDurationInSeconds % 3600) / 60
    val seconds = totalDurationInSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}


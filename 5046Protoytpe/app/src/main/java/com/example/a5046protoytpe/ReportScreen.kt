package com.example.a5046prototype

import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
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
    }

    val labels = todayExercises.mapIndexed { index, _ -> "Exercise ${index + 1}" }
    val distances = todayExercises.map { it.distance }

    val barEntries = distances.mapIndexed { index, distance ->
        BarEntry(index.toFloat(), distance)
    }
    val barDataSet = BarDataSet(barEntries, "Distance in KM")
    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val barData = BarData(barDataSet).apply {
        barWidth = 0.9f // Adjust bar width here
    }


    val totalDistanceToday = todayExercises.fold(0f) { sum, exercise -> sum + exercise.distance }
    val totalTimeToday = todayExercises.fold(0) { sum, exercise -> sum + exercise.time }
    // Calculate calories based on distance (Example: 100 calories per mile)
    val caloriesBurned = (totalTimeToday / 60 * 8.4  ).roundToInt() //Calories burned per minute = (MET(8) x body weight (60KG) in Kg x 3.5) ÷ 200


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formattedDate,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            MetricDisplay("Calories", "$caloriesBurned")
            MetricDisplay("Time", formatTime(totalTimeToday))
            MetricDisplay("Kilometers", "${"%.2f".format(totalDistanceToday)}")
        }
        Spacer(modifier = Modifier.height(20.dp))
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // Set the height for the chart
            factory = { context ->
                BarChart(context).apply {
                    data = barData
                    description.text = "Exercise Distances"
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    xAxis.setDrawGridLines(false)
                    axisLeft.setDrawGridLines(false)
                    axisRight.setDrawGridLines(false)
                    setFitBars(true)
                    animateY(500)

                    Log.d("ChartDebug", "Data set: $data") // Debug output
                    if (data != null && data.dataSetCount > 0) {
                        Log.d("ChartDebug", "Data loaded correctly")
                    } else {
                        Log.d("ChartDebug", "Data is empty")
                    }
                }
            },

            /* Force the chart to redraw, as the MPAndroidChart
            cannot render the chart at the first time that program start*/
            update = { chart ->
                chart.apply {
                    data = barData
                    invalidate()
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate("StartExercise") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Click to Start New Exercise")
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


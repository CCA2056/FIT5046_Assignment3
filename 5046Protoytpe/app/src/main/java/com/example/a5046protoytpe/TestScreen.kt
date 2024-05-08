package com.example.a5046protoytpe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Modifier



@Composable
fun TestScreen(exerciseViewModel: ExerciseViewModel) {
    val exercises by exerciseViewModel.allExercise.observeAsState(emptyList())
    val selectedExercise = remember { mutableStateOf<Exercise?>(null) }
    val insertDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { insertDialog.value = true }) {
            Text("Add Exercise")
        }
        LazyColumn {
            itemsIndexed(exercises) { index, exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onEdit = { selectedExercise.value = exercise },
                    onDelete = { exerciseViewModel.deleteExercise(exercise) }
                )
                Divider(color = Color.Gray, thickness = 5.dp)
            }
        }
    }
    if (insertDialog.value) {
        InsertExerciseDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { distance, time, date ->
                exerciseViewModel.insertExercise(Exercise(distance = distance, time = time, date = date))
            }
        )
    }
    selectedExercise.value?.let { exercise ->
        EditExerciseDialog(
            exercise = exercise,
            onDismiss = { selectedExercise.value = null },
            onSave = { updatedExercise ->
                exerciseViewModel.updateExercise(updatedExercise)
                selectedExercise.value = null
            }
        )
    }
}


@Composable
fun InsertExerciseDialog(
    onDismiss: () -> Unit,
    onSave: (Float, Int, String) -> Unit
) {
    var distance by remember { mutableStateOf("") }
    var timeInput by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Exercise") },
        confirmButton = {
            Button(
                onClick = {
                    val distanceFloat = distance.toFloatOrNull() ?: 0f // Convert or default to 0
                    val timeInt = timeInput.toIntOrNull() ?: 0 // Convert or default to 0
                    onSave(distanceFloat, timeInt, dateInput)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                TextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Enter Distance (m)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                TextField(
                    value = timeInput,
                    onValueChange = { timeInput = it },
                    label = { Text("Enter Time (seconds)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = dateInput,
                    onValueChange = { dateInput = it },
                    label = { Text("Enter Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default
                )


            }
        }
    )
}


@Composable
fun EditExerciseDialog(exercise: Exercise, onDismiss: () -> Unit, onSave: (Exercise) -> Unit)
{
    var editedExercise by remember { mutableStateOf(exercise) }
    var distanceText by remember { mutableStateOf("") }
    var timeText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Exercise") },
        confirmButton = {
            Button(
                onClick = {
                    val newDistance= distanceText.toFloatOrNull() ?: exercise.distance
                    val newTime = timeText.toIntOrNull() ?: exercise.time

                    onSave(editedExercise.copy(distance = newDistance, time = newTime ))
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                TextField(
                    value = distanceText,
                    onValueChange = { distanceText = it },
                    label = { Text("Distance (m)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                TextField(
                    value = timeText,
                    onValueChange = { timeText = it },
                    label = { Text("Time (seconds)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }
        }
    )
}
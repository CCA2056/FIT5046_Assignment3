package com.example.a5046protoytpe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.activity.viewModels


class MainActivity : ComponentActivity() {
    private val viewModel: ExerciseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
            //TestScreen(viewModel)
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        // Assuming you have a colorScheme and typography set up
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

package com.example.a5046protoytpe

import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a5046prototype.AccountScreen
import com.example.a5046prototype.LoginScreen
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.a5046prototype.ReportScreen
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyApp1()
            MaterialTheme {
                val navController = rememberNavController()

                // Check if the user is already signed in
                val firebaseAuth = FirebaseAuth.getInstance()
                val isSignedOut by remember {
                    mutableStateOf(firebaseAuth.currentUser == null)
                }
                val exerciseViewModel: ExerciseViewModel = viewModel()
                val context = LocalContext.current
                val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                NavHost(navController = navController, startDestination = "loginScreen") {
                    composable("loginScreen") { LoginScreen(navController, isSignedOut) }
                    composable("accountScreen") { AccountScreen(navController) }
                    composable("homePage") { HomePage(navController) }
                    composable("StartExercise") { StartExercisePage(navController, exerciseViewModel) }
                    composable("StepCounting") { StepCountingPage(sensorManager, navController) }
                    composable("Report") { ReportScreen(exerciseViewModel, navController, LocalDate.now()) }
                    composable(
                        route = "ReportScreen/{selectedDate}",
                        arguments = listOf(navArgument("selectedDate") { type = NavType.StringType })
                    ) { backStackEntry ->
                        // Parse the date from the argument
                        val selectedDateString = backStackEntry.arguments?.getString("selectedDate") ?: ""
                        val selectedDate = LocalDate.parse(selectedDateString, DateTimeFormatter.ISO_LOCAL_DATE)
                        ReportScreen(exerciseViewModel, navController, selectedDate)
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp1() {
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



/*@Preview(showBackground = true)
@Composable
//fun DefaultPreview() {
//    MyApp()
//}

fun PreviewLoginScreen() {
    val navController = rememberNavController()

    MaterialTheme {
        LoginScreen(navController)
    }
}*/

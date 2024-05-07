package com.example.a5046protoytpe

import RegisterViewModel
import android.content.Context
import android.hardware.SensorManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a5046prototype.AccountScreen
import com.example.a5046prototype.LoginScreen
import com.example.a5046prototype.ReportScreen
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(64)
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val exerciseViewModel: ExerciseViewModel = viewModel()
    val firebaseAuth = FirebaseAuth.getInstance()
    val isSignedOut by remember {
        mutableStateOf(firebaseAuth.currentUser == null)
    }
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val weatherViewModel: WeatherViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            // Only show the BottomNavigation if the current route is not the login screen
            if (currentDestination?.route != "loginScreen" && currentDestination?.route != "accountScreen" ) {
                BottomNavigation(backgroundColor = Color.LightGray) {
                    NavBarItem().NavBarItems().forEach { navItem ->
                        BottomNavigationItem(
                            icon = {
                                when (navItem.route) {
                                    Routes.HomePage.value -> Icon(
                                        painter = painterResource(id = R.drawable.homebutton),
                                        contentDescription = "Home",
                                        tint = Color.Unspecified  // Use the SVG's original colors
                                    )
                                    Routes.Profile.value -> Icon(
                                        painter = painterResource(id = R.drawable.profilebutton),
                                        contentDescription = "Profile",
                                        tint = Color.Unspecified  // Use the SVG's original colors
                                    )
                                    Routes.Exercise.value -> Icon(
                                        painter = painterResource(id = R.drawable.exercisebutton),
                                        contentDescription = "Exercise",
                                        tint = Color.Unspecified  // Use the SVG's original colors
                                    )
                                }
                            },
                            label = { Text(navItem.label) },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == navItem.route
                            } == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    // Pop up to a given destination before navigating
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Launch only one instance of the destination
                                    launchSingleTop = true
                                    // Restore state during navigation
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "loginScreen") {
            composable("loginScreen") { LoginScreen(navController, isSignedOut) }
            composable("accountScreen") { AccountScreen(navController) }
            composable("homePage") { HomePage(navController, weatherViewModel) }
            composable("StartExercise") { StartExercisePage(navController, exerciseViewModel) }
            composable("StepCounting") { StepCountingPage(sensorManager, navController) }
            // Ensure that there's only one route for "Report" that takes a date parameter
            composable(
                route = "Report/{selectedDate}",
                arguments = listOf(navArgument("selectedDate") { type = NavType.StringType })
            ) { backStackEntry ->
                // Parse the date from the argument
                val selectedDateString = backStackEntry.arguments?.getString("selectedDate") ?: throw IllegalStateException("Date is required")
                val selectedDate = LocalDate.parse(selectedDateString, DateTimeFormatter.ISO_LOCAL_DATE)
                ReportScreen(exerciseViewModel, navController, selectedDate)
            }
            composable("Profile") { ProfilePage(navController, registerViewModel) }
            composable("EditProfile") { EditProfilePage(navController, registerViewModel) }
        }
    }
}

@RequiresApi(64)
@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar()
}

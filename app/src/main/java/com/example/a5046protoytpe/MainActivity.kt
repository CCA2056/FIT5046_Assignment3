package com.example.a5046protoytpe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a5046prototype.AccountScreen
import com.example.a5046prototype.LoginScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "loginScreen") {
                    composable("loginScreen") { LoginScreen(navController) }
                    composable("accountScreen") { AccountScreen(navController) }
                    composable("homePage") { HomePage(navController) }
                }
            }
        }
    }
}


@Composable
fun MyAppUI() {
    MaterialTheme {
        // Assuming you have a colorScheme and typography set up
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            /*StartExercisePage()*/
            StepCountingPage()
        }
    }
}



@Preview(showBackground = true)
@Composable
//fun DefaultPreview() {
//    MyApp()
//}
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        LoginScreen(navController)
    }
}

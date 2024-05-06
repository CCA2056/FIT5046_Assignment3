package com.example.login_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

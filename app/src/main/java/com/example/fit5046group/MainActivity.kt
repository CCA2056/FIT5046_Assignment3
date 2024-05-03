package com.example.fit5046group

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
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
            //Profile Page
            //ProfilePage()

            //Edit Profile Page
            EditProfilePage()
        }
    }
}

@Preview(showBackground = true)
@Composable
/*fun ProfilePreview() {
    MyApp()
}*/

fun EditProfilePreview() {
    MyApp()
}
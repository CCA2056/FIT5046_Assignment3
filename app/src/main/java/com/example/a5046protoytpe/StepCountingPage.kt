package com.example.a5046protoytpe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StepCountingPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = Color(0xFFFDEEE9))
                .fillMaxWidth()
        ) {
            Text(
                text = "00:00:16",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp
                )
            )
            Text(
                text = "Distance:0.16KM",
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = "Speed: 0.3 KM/MIN",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
        Button(
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFfe703f) // Replace with your color
            )
        ) {
            Text(text = "Start", color = Color.White)
        }
    }
}
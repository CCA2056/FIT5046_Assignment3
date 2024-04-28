package com.example.a5046protoytpe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartExercisePage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(color = Color(0xFFFDEEE9))
                .fillMaxWidth()
        ) {
            Text(
                text = "0.5KM",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp
                )
            )
            Text(
                text = "Total Running Distance",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)

        ) {
            // Row for Running Tips header with icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDEEE9),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )

                    )
                    .padding(10.dp)
                    .fillMaxWidth()


            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lightning), // Replace with your icon resource
                    contentDescription = "Running Tips",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Running Tips",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            // Column for Running Tips list items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFDEEE9), // Replace with the actual color from your design
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomEnd = 30.dp,
                            bottomStart = 30.dp
                        )
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "1. Warm up thoroughly first")
                Text(text = "2. Keep exercising for more than 30 minutes")
                Text(text = "3. Click the start button below to start exercising")
            }
        }

        Button(
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                // Replace .fillMaxWidth().height(48.dp) with .size() for a circle
                .size(100.dp), // Example size, adjust as necessary
            shape = CircleShape, // This makes the button circular
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFfe703f) // Your custom color
            )
        ){
            Text(text = "Start", color = Color.White, fontSize = 20.sp)
        }
    }
}
package com.example.fit5046group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfilePage() {
    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color(0xFFFDEEE9))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))

            Button(
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
                .height(48.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFfe703f)              )
        ) {
            Text(text = "<", fontWeight = FontWeight.Bold, color = Color.White)
        }}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                //add padding from the top
                .padding(top = 100.dp)
        ) {
            Text(
                text = "Your Profile",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            )

            Spacer(modifier = Modifier.height(120.dp))

            //Image of the user profile
            Image(
                painter = painterResource(id = R.drawable.pic_profile),
                contentDescription = "Image of Your Profile",
                modifier = Modifier.size(65.dp) // Set the size of the image
            )

            // Add padding between the title and the new text
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Linh Nguyen",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 24.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { /* TODO: Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFfe703f)                )
            ) {
                Text(text = "Change Password", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFfe703f)                )
            ) {
                Text(text = "Log Out", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}
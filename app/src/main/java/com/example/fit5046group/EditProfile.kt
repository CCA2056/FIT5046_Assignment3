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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun EditProfilePage(navController: NavController) {
    var password by remember { mutableStateOf("")}
    var confirmed by remember { mutableStateOf("")}

    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color(0xFFFDEEE9)))
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("Profile") },
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
                text = "Edit Profile",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Image of the user profile
            Image(
                painter = painterResource(id = R.drawable.pic_profile),
                contentDescription = "Image of Your Profile",
                modifier = Modifier.size(65.dp) // Set the size of the image
            )

            // Add padding between the title and the new text
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Linh Nguyen",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 24.sp
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                //add padding from the top
                .padding(top = 400.dp)
        ) {
            if (password.isNotEmpty()) {
                Text(
                    text = "Enter New Password",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("New Password") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (confirmed.isNotEmpty()) {
                Text(
                    text = "Confirm New Password",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            OutlinedTextField(
                value = confirmed,
                onValueChange = { confirmed = it },
                label = { Text("Confirm Password") }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { /* TODO: Handle Click and Error Messages with PWD Validation*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFfe703f)                )
            ) {
                Text(text = "Save", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}
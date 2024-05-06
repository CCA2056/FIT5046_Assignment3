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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun EditProfilePage(navController: NavController, viewModel: RegisterViewModel) {
    val userName = remember { viewModel.getUserName()}

    var password by remember { mutableStateOf("")}
    var confirmed by remember { mutableStateOf("")}
    var error by remember { mutableStateOf(false) }
    var invalidPassword by remember { mutableStateOf(false) }
    var passwordSaved by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmedPasswordVisible by remember { mutableStateOf(false) }

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
                text = userName,
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
                label = { Text("New Password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        val icon: ImageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(
                            imageVector = icon,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
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
                label = { Text("Confirm Password") },
                visualTransformation = if (isConfirmedPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { isConfirmedPasswordVisible = !isConfirmedPasswordVisible }
                    ) {
                        val icon: ImageVector = if (isConfirmedPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(
                            imageVector = icon,
                            contentDescription = if (isConfirmedPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            fun isPasswordValid(password: String): Boolean {
                val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=\\S+\$).{8,}\$")
                return passwordRegex.matches(password)
            }

            if (error) {
                Text(
                    text = "Passwords do not match or empty!",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (invalidPassword) {
                Text(
                    text = "Password should have at least 8 characters, including 1 uppercase, 1 number, and 1 special character.",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (passwordSaved) {
                Text(
                    text = "New Password Saved :)",
                    color = Color(0xFFfe703f),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LaunchedEffect(Unit) {
                    // Navigate to "Profile" destination after a delay of 2 seconds
                    delay(2000)
                    navController.navigate("Profile")
                }
            }

            Button(
                onClick = { if (password == confirmed && password.isNotEmpty()) {
                    if (isPasswordValid(password)) {
                        // Password and confirmed passwords match, proceed with saving
                        passwordSaved = true
                    } else {
                        // Password provided does not meet validation criteria
                        invalidPassword = true
                    }
                } else {
                    // Passwords don't match, one of them is empty, or password is invalid, show error messages
                    error = true
                } },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFfe703f)                )
            ) {
                Text(text = "Save", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            LaunchedEffect(error, passwordSaved, invalidPassword) {
                // Hide the error message and passwordSaved message after 5.5 seconds
                delay(5500)
                error = false
                passwordSaved = false
                invalidPassword = false
            }
        }
    }
}
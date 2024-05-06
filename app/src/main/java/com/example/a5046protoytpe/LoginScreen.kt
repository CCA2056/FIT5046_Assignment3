package com.example.a5046prototype

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a5046protoytpe.MyApp
import com.example.a5046protoytpe.ViewModelFactory

@Composable
fun LoginScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as MyApp
    val viewModel: LoginViewModel = viewModel(factory = ViewModelFactory(app.preferencesHelper))

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isUsingPhone by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(16.dp))

            // Toggle for Email or Phone Number
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { isUsingPhone = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isUsingPhone) Color(0xFFFF9800) else Color.LightGray
                    )
                ) {
                    Text("Email", color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { isUsingPhone = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isUsingPhone) Color(0xFFFF9800) else Color.LightGray
                    )
                ) {
                    Text("Phone Number", color = Color.White)
                }
            }

            // Email or Phone Number Input
            if (!isUsingPhone) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Id") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.loginWithEmail(email, password) { success ->
                                if (success) {
                                    navController.navigate("homePage")
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Incorrect email or password",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Login", color = Color.White)
                }
            }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = {
                navController.navigate("accountScreen")
            }) {
                Text("Create Account")
            }

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            // Google Sign-In
            OutlinedButton(
                onClick = {
                    // Implement Google Sign-In logic
                },
                colors = ButtonDefaults.outlinedButtonColors() // Add the colors you want here
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color.Unspecified // This is to ensure the icon color inherits from the text or specified color
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Google Sign in" )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}

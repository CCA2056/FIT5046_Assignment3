package com.example.a5046prototype

import RegisterViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a5046protoytpe.MyApp
import com.example.a5046protoytpe.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Typography


@Composable
fun AccountScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as MyApp
    val viewModel: RegisterViewModel = viewModel(factory = ViewModelFactory(app.preferencesHelper))
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var agreeTerms by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if(viewModel.registerSuccess.value){
        navController.navigate("loginScreen")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Join us to start Tracking Runners!"
//                    , style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Discover your perfect running record."
//                    , style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Create Account"
//                    , style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },  // 确认密码输入框
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = agreeTerms,
                        onCheckedChange = { agreeTerms = it }
                    )
                    Text("I agree to the Terms and Conditions")
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (agreeTerms) {
                            if (password != confirmPassword) {
                                errorMessage = "Passwords do not match"
                                showError = true
                            } else {
                                viewModel.register(name, email, password, confirmPassword, phone)
                                showError = false
                            }
                        } else {
                            errorMessage = "You must agree to the terms and conditions"
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                ) {
                    Text("Join us")
                }
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { navController.navigate("loginScreen") }) {
                    Text("Already have an account? Login")
                }
            }
        }
    )

    LaunchedEffect(showError) {
        if (showError) {
            snackbarHostState.showSnackbar(
                message = "You must agree to the terms and conditions",
                duration = SnackbarDuration.Short
            )
            showError = false
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAccountScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        AccountScreen(navController)
    }
}

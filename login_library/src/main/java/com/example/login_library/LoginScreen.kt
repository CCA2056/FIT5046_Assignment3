package com.example.login_library

import LoginViewModel
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a5046protoytpe.R
import com.google.android.gms.common.api.ApiException
import androidx.activity.ComponentActivity

@Composable
fun LoginScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as MyApp
    val viewModel: LoginViewModel = viewModel(factory = ViewModelFactory(app.preferencesHelper))

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isUsingPhone by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Firebase Authentication
    val firebaseAuth = FirebaseAuth.getInstance()

    // Google Sign-In options
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("629710888894-i3ng3rv1a46j4aqs4hh80otke34rbbgf.apps.googleusercontent.com")
        .requestEmail()
        .build()


    // Google Sign-In client
    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)

    // Function to authenticate with Firebase using Google Sign-In
    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    // Navigate to the home page or handle sign-in success
                    navController.navigate("homePage")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // Handle sign-in failure
                }
            }
    }

    // Activity Result Launcher for Google Sign-In
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Google Sign-In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign-In failed
            Log.e(TAG, "Google sign in failed", e)
        }
    }


    // Function to start Google Sign-In
    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }


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
            if (isUsingPhone) {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Implement OTP request logic here
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Request OTP", color = Color.White)
                }
            } else {
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
                    visualTransformation = if (password.isNotEmpty()) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.loginWithEmail(email, password) { success ->
                                if (success) navController.navigate("homePage")
                                else {
                                    // Handle login error
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
                onClick = { signInWithGoogle() },
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

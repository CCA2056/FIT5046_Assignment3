package com.example.a5046prototype

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
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState

@Composable
fun LoginScreen(navController: NavController, isSignedOut: Boolean) {

    val app = LocalContext.current.applicationContext as MyApp
    val viewModel: LoginViewModel = viewModel(factory = ViewModelFactory(app.preferencesHelper))

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isUsingPhone by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isSignedOut by remember { mutableStateOf(false) }

    // Firebase Authentication
    val firebaseAuth = FirebaseAuth.getInstance()

    // Check if user is already signed in with Google
    val isGoogleSignedIn = firebaseAuth.currentUser?.providerData?.any { userInfo ->
        userInfo.providerId == GoogleAuthProvider.PROVIDER_ID
    } ?: false

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

    // Function to sign out from Google account
    fun signOutFromGoogle() {
        firebaseAuth.signOut()
        googleSignInClient.signOut()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Display a snackbar message on successful sign-out
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "You have been successfully logged out.",
                            duration = SnackbarDuration.Short
                        )
                    }
                    // Handle sign-out completion
                    // Toggle the isSignedOut state to trigger recomposition
                    isSignedOut = true
                } else {
                    // Optionally handle errors here
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "Failed to log out.",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
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
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Email or Phone Number Input
            if (!isUsingPhone) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter Your Email") },
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
                SnackbarHost(hostState = snackbarHostState)
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.loginWithEmail(email, password) { success, errorMessage ->
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
            Button(
                onClick = {
                    navController.navigate("accountScreen")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800) // Orange color like the Sign Out button
                )
            ) {
                Text("Create Account", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            // Google Sign-In Button Conditionally Displayed
            if (!isGoogleSignedIn) {
                OutlinedButton(
                    onClick = {
                        signInWithGoogle()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800) // Set the button color to orange
                    )
                ) {
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Google Sign in")
                }
            }
            // Sign-out button (if user is signed in)
            if (firebaseAuth.currentUser != null) {
                Button(
                    onClick = { signOutFromGoogle() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800) // Set the button color to orange
                    )
                ) {
                    Text("Click to Sign Out")
                }
            }
        }

    }
    // Refresh the screen when isSignedOut changes
    if (isSignedOut) {
        // Reset the isSignedOut state to false
        isSignedOut = false
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        var isSignedOut by remember { mutableStateOf(false) }
        LoginScreen(navController, isSignedOut)
    }
}

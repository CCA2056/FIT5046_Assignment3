package com.example.login_library

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class LoginViewModel(private val prefs: PreferencesHelper) : ViewModel() {
    var isLoggedIn = mutableStateOf(false)

    fun login(email: String, password: String) {
        val (savedEmail, savedPassword) = prefs.getUser()
        isLoggedIn.value = email == savedEmail && password == savedPassword
    }

    fun requestOtp(phoneNumber: String) {
        // Implement your OTP request logic here
        // Example: set some state or send an API call to request an OTP
    }

    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit) {
        val (savedEmail, savedPassword) = prefs.getUser()
        callback(email == savedEmail && password == savedPassword)
    }
}


class RegisterViewModel(private val prefs: PreferencesHelper) : ViewModel() {
    var registerSuccess = mutableStateOf(false)

    fun register(email: String, password: String, confirmPassword: String, phone: String) {
        if (password == confirmPassword) {
            prefs.saveUser(email, password)
            registerSuccess.value = true
        } else {
            registerSuccess.value = false
        }
    }

}


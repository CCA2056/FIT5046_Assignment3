package com.example.a5046protoytpe

import LoginViewModel
import PreferencesHelper
import RegisterViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val prefs: PreferencesHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(prefs) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

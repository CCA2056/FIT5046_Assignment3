package com.example.login_library

import PreferencesHelper
import android.app.Application

class MyApp : Application() {
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate() {
        super.onCreate()
        // 初始化 PreferencesHelper
        preferencesHelper = PreferencesHelper(applicationContext)
    }
}

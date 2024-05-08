package com.example.a5046protoytpe

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

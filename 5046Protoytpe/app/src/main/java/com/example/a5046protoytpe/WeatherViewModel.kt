package com.example.a5046protoytpe

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers

class WeatherViewModel : ViewModel() {
    private val _temperature = mutableStateOf("Loading...")
    val temperature: State<String> = _temperature

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) { // Specify the IO dispatcher
            try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=Melbourne,au&units=metric&appid=559908408663b4f0dc396859cadc99b9")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    val jsonObject = JSONObject(response.toString())
                    val main = jsonObject.getJSONObject("main")
                    val temp = main.getDouble("temp")
                    _temperature.value = "$temp°C"
                } else {
                    _temperature.value = "Failed to fetch with response code: $responseCode"
                }
            } catch (e: Exception) {
                _temperature.value = "Error: ${e.message}"
            }
        }
    }
}
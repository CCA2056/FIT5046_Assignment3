package com.example.a5046protoytpe

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Headers

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}
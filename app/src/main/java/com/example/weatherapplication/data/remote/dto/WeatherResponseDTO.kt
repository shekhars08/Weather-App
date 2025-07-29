package com.example.weatherapplication.data.remote.dto

import com.squareup.moshi.Json

data class WeatherResponseDTO(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDTO
)

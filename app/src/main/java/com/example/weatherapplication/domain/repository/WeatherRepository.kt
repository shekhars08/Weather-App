package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.utils.Resource
import com.example.weatherapplication.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}
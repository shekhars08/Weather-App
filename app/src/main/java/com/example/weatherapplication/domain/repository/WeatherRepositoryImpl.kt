package com.example.weatherapplication.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.data.mappers.toWeatherInfo
import com.example.weatherapplication.data.remote.WeatherAPI
import com.example.weatherapplication.domain.utils.Resource
import com.example.weatherapplication.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
): WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(
        lat: Double,
        lon: Double
    ): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeather(
                    lat = lat,
                    lon = lon
                ).toWeatherInfo()
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}
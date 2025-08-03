package com.example.weatherapplication.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.data.remote.dto.WeatherDataDTO
import com.example.weatherapplication.data.remote.dto.WeatherResponseDTO
import com.example.weatherapplication.domain.WeatherType
import com.example.weatherapplication.domain.weather.WeatherData
import com.example.weatherapplication.domain.weather.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexWeatherData(
    val index: Int,
    val data: WeatherData
)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDTO.toWeatherDataMap() : Map<Int, List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidity[index]
        IndexWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)

            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun WeatherResponseDTO.toWeatherInfo() : WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour+1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}
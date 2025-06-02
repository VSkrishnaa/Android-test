
package com.Rahul.weathertrack.data.network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String
)
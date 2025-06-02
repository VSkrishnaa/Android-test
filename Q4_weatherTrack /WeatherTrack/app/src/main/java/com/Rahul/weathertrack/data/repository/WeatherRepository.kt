
package com.Rahul.weathertrack.data.repository

import androidx.lifecycle.LiveData
import com.Rahul.weathertrack.data.database.WeatherDao
import com.Rahul.weathertrack.data.database.WeatherEntity
import com.Rahul.weathertrack.data.network.WeatherApiService
import com.Rahul.weathertrack.utils.Constants
import com.Rahul.weathertrack.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val apiService: WeatherApiService
) {

    fun getAllWeatherRecords(): LiveData<List<WeatherEntity>> {
        return weatherDao.getAllWeatherRecords()
    }

    suspend fun fetchAndSaveWeather(city: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCurrentWeather(
                    city = city,
                    apiKey = Constants.API_KEY
                )

                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val weatherEntity = WeatherEntity(
                            temperature = weatherResponse.main.temp,
                            humidity = weatherResponse.main.humidity,
                            condition = weatherResponse.weather[0].main,
                            timestamp = System.currentTimeMillis(),
                            date = DateUtils.getCurrentDate()
                        )
                        weatherDao.insertWeather(weatherEntity)
                        Result.success("Weather updated successfully")
                    } ?: Result.failure(Exception("Empty response"))
                } else {
                    Result.failure(Exception("API Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getWeatherForLastWeek(): List<WeatherEntity> {
        return withContext(Dispatchers.IO) {
            val startDate = DateUtils.getDateWeekAgo()
            weatherDao.getWeatherForLastWeek(startDate)
        }
    }

    suspend fun getWeatherForDate(date: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            weatherDao.getWeatherForDate(date)
        }
    }
}
package com.Rahul.weathertrack.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_records ORDER BY timestamp DESC")
    fun getAllWeatherRecords(): LiveData<List<WeatherEntity>>

    @Query("SELECT * FROM weather_records WHERE date >= :startDate ORDER BY timestamp DESC")
    suspend fun getWeatherForLastWeek(startDate: String): List<WeatherEntity>

    @Query("SELECT * FROM weather_records WHERE date = :date ORDER BY timestamp DESC LIMIT 1")
    suspend fun getWeatherForDate(date: String): WeatherEntity?

    @Query("DELETE FROM weather_records WHERE timestamp < :cutoffTime")
    suspend fun deleteOldRecords(cutoffTime: Long)
}

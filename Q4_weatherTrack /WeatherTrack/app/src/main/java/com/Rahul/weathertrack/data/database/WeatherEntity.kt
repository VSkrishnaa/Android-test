package com.Rahul.weathertrack.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_records")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val temperature: Double,
    val humidity: Int,
    val condition: String,
    val timestamp: Long,
    val date: String // Format: "2024-01-15"
)
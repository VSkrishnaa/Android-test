
package com.Rahul.weathertrack.ui.summary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.Rahul.weathertrack.data.database.WeatherDatabase
import com.Rahul.weathertrack.data.database.WeatherEntity
import com.Rahul.weathertrack.data.network.WeatherApiService
import com.Rahul.weathertrack.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeeklySummaryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository

    private val _weeklyWeather = MutableLiveData<List<WeatherEntity>>()
    val weeklyWeather: LiveData<List<WeatherEntity>> = _weeklyWeather

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        val database = WeatherDatabase.getDatabase(application)
        val apiService = WeatherApiService.create()
        repository = WeatherRepository(database.weatherDao(), apiService)
        loadWeeklyData()
    }

    private fun loadWeeklyData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = repository.getWeatherForLastWeek()
                _weeklyWeather.value = data
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
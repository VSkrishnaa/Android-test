
package com.Rahul.weathertrack.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.Rahul.weathertrack.data.database.WeatherDatabase
import com.Rahul.weathertrack.data.database.WeatherEntity
import com.Rahul.weathertrack.data.network.WeatherApiService
import com.Rahul.weathertrack.data.repository.WeatherRepository
import com.Rahul.weathertrack.utils.Constants
import com.Rahul.weathertrack.utils.NetworkUtils
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    val allWeatherRecords: LiveData<List<WeatherEntity>>

    init {
        val database = WeatherDatabase.getDatabase(application)
        val apiService = WeatherApiService.create()
        repository = WeatherRepository(database.weatherDao(), apiService)
        allWeatherRecords = repository.getAllWeatherRecords()
    }

    fun refreshWeather() {
        if (!NetworkUtils.isNetworkAvailable(getApplication())) {
            _errorMessage.value = "No internet connection available"
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.fetchAndSaveWeather(Constants.DEFAULT_CITY)
            _isLoading.value = false

            result.fold(
                onSuccess = { message ->
                    _successMessage.value = message
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "Unknown error occurred"
                }
            )
        }
    }
}

package com.Rahul.weathertrack.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.Rahul.weathertrack.data.database.WeatherDatabase
import com.Rahul.weathertrack.data.network.WeatherApiService
import com.Rahul.weathertrack.data.repository.WeatherRepository
import com.Rahul.weathertrack.utils.Constants
import com.Rahul.weathertrack.utils.NetworkUtils

class WeatherSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            if (!NetworkUtils.isNetworkAvailable(applicationContext)) {
                return Result.retry()
            }

            val database = WeatherDatabase.getDatabase(applicationContext)
            val apiService = WeatherApiService.create()
            val repository = WeatherRepository(database.weatherDao(), apiService)

            val result = repository.fetchAndSaveWeather(Constants.DEFAULT_CITY)

            if (result.isSuccess) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
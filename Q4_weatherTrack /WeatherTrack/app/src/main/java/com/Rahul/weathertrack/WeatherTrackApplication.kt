
package com.Rahul.weathertrack

import android.app.Application
import androidx.work.*
import com.Rahul.weathertrack.data.worker.WeatherSyncWorker
import com.Rahul.weathertrack.utils.Constants
import java.util.concurrent.TimeUnit

class WeatherTrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupPeriodicWeatherSync()
    }

    private fun setupPeriodicWeatherSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWork = PeriodicWorkRequestBuilder<WeatherSyncWorker>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }
}
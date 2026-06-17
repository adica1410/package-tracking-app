package com.example.packagefortracking.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkScheduler {

    private const val TRACKING_REFRESH_WORK = "tracking_refresh_work"

    fun scheduleTrackingRefresh(context: Context) {
        val request = PeriodicWorkRequestBuilder<TrackingRefreshWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            TRACKING_REFRESH_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelTrackingRefresh(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(
            TRACKING_REFRESH_WORK
        )
    }
}
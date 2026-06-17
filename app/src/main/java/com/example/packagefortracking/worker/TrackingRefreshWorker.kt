package com.example.packagefortracking.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.packagefortracking.data.AppDatabase
import com.example.packagefortracking.data.AppPreferences
import com.example.packagefortracking.notification.NotificationHelper
import com.example.packagefortracking.tracking.TrackingProviderFactory

class TrackingRefreshWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val preferences = AppPreferences(applicationContext)

        if (!preferences.isAutoRefreshEnabled()) {
            return Result.success()
        }

        val dao = AppDatabase.getDatabase(applicationContext).packageDao()
        val packages = dao.getAllPackagesOnce()

        packages.forEach { packageEntity ->
            val provider = TrackingProviderFactory.createProvider(
                packageEntity.postalService
            )

            val result = provider.track(packageEntity.trackingNumber)

            val updatedPackage = packageEntity.copy(
                status = result.status,
                lastUpdate = result.lastUpdate,
                estimatedDelivery = result.estimatedDelivery
            )

            dao.updatePackage(updatedPackage)

            if (
                preferences.isNotificationsEnabled() &&
                packageEntity.status != updatedPackage.status
            ) {
                NotificationHelper.showPackageNotification(
                    context = applicationContext,
                    title = "Package status updated",
                    message = "${packageEntity.name}: ${updatedPackage.status}"
                )
            }
        }

        return Result.success()
    }
}
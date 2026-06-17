package com.example.packagefortracking

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.packagefortracking.data.AppDatabase
import com.example.packagefortracking.data.PackageRepository
import com.example.packagefortracking.navigation.PackageNavigation
import com.example.packagefortracking.notification.NotificationHelper
import com.example.packagefortracking.ui.theme.PackageForTrackingTheme
import com.example.packagefortracking.viewmodel.PackageViewModel
import com.example.packagefortracking.viewmodel.PackageViewModelFactory

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHelper.createNotificationChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PackageRepository(database.packageDao())
        val factory = PackageViewModelFactory(repository)

        setContent {
            val packageViewModel: PackageViewModel = viewModel(factory = factory)

            PackageForTrackingTheme {
                PackageNavigation(
                    packageViewModel = packageViewModel
                )
            }
        }
    }
}
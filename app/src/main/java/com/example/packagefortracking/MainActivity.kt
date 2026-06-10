package com.example.packagefortracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.packagefortracking.navigation.PackageNavigation
import com.example.packagefortracking.ui.theme.PackageForTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PackageForTrackingTheme {
                PackageNavigation()
            }
        }
    }
}
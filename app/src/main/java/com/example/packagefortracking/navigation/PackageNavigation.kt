package com.example.packagefortracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.packagefortracking.ui.screens.AddPackageScreen
import com.example.packagefortracking.ui.screens.ArchiveScreen
import com.example.packagefortracking.ui.screens.HomeScreen
import com.example.packagefortracking.ui.screens.PackageDetailsScreen
import com.example.packagefortracking.ui.screens.SettingsScreen
import com.example.packagefortracking.ui.screens.StatisticsScreen
import com.example.packagefortracking.ui.screens.EditPackageScreen

@Composable
fun PackageNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                onAddPackageClick = {
                    navController.navigate(AppScreen.AddPackage.route)
                },
                onPackageClick = {
                    navController.navigate(AppScreen.PackageDetails.route)
                },
                onArchiveClick = {
                    navController.navigate(AppScreen.Archive.route)
                },
                onStatisticsClick = {
                    navController.navigate(AppScreen.Statistics.route)
                },
                onSettingsClick = {
                    navController.navigate(AppScreen.Settings.route)
                }
            )
        }

        composable(AppScreen.AddPackage.route) {
            AddPackageScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.PackageDetails.route) {
            PackageDetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = {
                    navController.navigate(AppScreen.EditPackage.route)
                }
            )
        }

        composable(AppScreen.Archive.route) {
            ArchiveScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.Statistics.route) {
            StatisticsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.EditPackage.route) {
            EditPackageScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
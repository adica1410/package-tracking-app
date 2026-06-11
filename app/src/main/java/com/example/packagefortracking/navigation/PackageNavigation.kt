package com.example.packagefortracking.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.ui.screens.*
import com.example.packagefortracking.viewmodel.PackageViewModel

@Composable
fun PackageNavigation(
    packageViewModel: PackageViewModel
) {
    val navController = rememberNavController()

    val activePackages by packageViewModel.activePackages.collectAsState()
    val archivedPackages by packageViewModel.archivedPackages.collectAsState()
    val allPackages by packageViewModel.allPackages.collectAsState()

    var selectedPackage by remember { mutableStateOf<PackageEntity?>(null) }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                packages = activePackages,
                onAddPackageClick = {
                    navController.navigate(AppScreen.AddPackage.route)
                },
                onPackageClick = { packageEntity ->
                    selectedPackage = packageEntity
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
                },
                onSavePackage = { packageEntity ->
                    packageViewModel.addPackage(packageEntity)
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.PackageDetails.route) {
            PackageDetailsScreen(
                packageEntity = selectedPackage,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = {
                    navController.navigate(AppScreen.EditPackage.route)
                },
                onArchiveClick = { updatedPackage ->
                    packageViewModel.updatePackage(updatedPackage)
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.EditPackage.route) {
            EditPackageScreen(
                packageEntity = selectedPackage,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveChanges = { updatedPackage ->
                    packageViewModel.updatePackage(updatedPackage)
                    selectedPackage = updatedPackage
                    navController.popBackStack()
                },
                onDeletePackage = { packageToDelete ->
                    packageViewModel.deletePackage(packageToDelete)
                    selectedPackage = null
                    navController.popBackStack(AppScreen.Home.route, inclusive = false)
                }
            )
        }

        composable(AppScreen.Archive.route) {
            ArchiveScreen(
                packages = archivedPackages,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreen.Statistics.route) {
            StatisticsScreen(
                packages = allPackages,
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
    }
}
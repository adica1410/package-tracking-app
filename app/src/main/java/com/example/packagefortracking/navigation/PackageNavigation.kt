package com.example.packagefortracking.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.ui.screens.*
import com.example.packagefortracking.viewmodel.PackageViewModel
import kotlinx.coroutines.launch
import com.example.packagefortracking.ui.screens.SplashScreen

@Composable
fun PackageNavigation(
    packageViewModel: PackageViewModel
) {
    val navController = rememberNavController()

    val activePackages by packageViewModel.activePackages.collectAsState()
    val archivedPackages by packageViewModel.archivedPackages.collectAsState()
    val allPackages by packageViewModel.allPackages.collectAsState()

    var selectedPackage by remember { mutableStateOf<PackageEntity?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Splash.route) {
                SplashScreen(
                    onSplashFinished = {
                        navController.navigate(AppScreen.Home.route) {
                            popUpTo(AppScreen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
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

                        scope.launch {
                            snackbarHostState.showSnackbar("Package saved successfully")
                        }
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
                        selectedPackage = updatedPackage
                        navController.popBackStack()

                        scope.launch {
                            snackbarHostState.showSnackbar("Package moved to archive")
                        }
                    },
                    onRefreshClick = { packageEntity ->
                        packageViewModel.refreshPackageAndReturn(packageEntity) { updatedPackage ->
                            selectedPackage = updatedPackage

                            scope.launch {
                                snackbarHostState.showSnackbar("Tracking status refreshed")
                            }
                        }
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

                        scope.launch {
                            snackbarHostState.showSnackbar("Package updated successfully")
                        }
                    },
                    onDeletePackage = { packageToDelete ->
                        packageViewModel.deletePackage(packageToDelete)
                        selectedPackage = null
                        navController.popBackStack(AppScreen.Home.route, inclusive = false)

                        scope.launch {
                            snackbarHostState.showSnackbar("Package deleted")
                        }
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
}
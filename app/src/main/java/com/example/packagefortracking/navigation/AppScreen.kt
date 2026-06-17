package com.example.packagefortracking.navigation

sealed class AppScreen(val route: String) {
    data object Splash : AppScreen("splash")
    data object Home : AppScreen("home")
    data object AddPackage : AppScreen("add_package")
    data object PackageDetails : AppScreen("package_details")
    data object Archive : AppScreen("archive")
    data object Statistics : AppScreen("statistics")
    data object Settings : AppScreen("settings")
    data object EditPackage : AppScreen("edit_package")
}
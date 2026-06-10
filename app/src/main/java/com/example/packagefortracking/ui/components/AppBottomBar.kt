package com.example.packagefortracking.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.packagefortracking.ui.theme.Accent
import com.example.packagefortracking.ui.theme.Primary
import com.example.packagefortracking.ui.theme.TextMuted

@Composable
fun AppBottomBar(
    selectedItem: String,
    onHomeClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(78.dp),
        containerColor = Primary,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedItem == "home",
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = bottomBarColors()
        )

        NavigationBarItem(
            selected = selectedItem == "archive",
            onClick = onArchiveClick,
            icon = { Icon(Icons.Default.Archive, contentDescription = "Archive") },
            label = { Text("Archive") },
            colors = bottomBarColors()
        )

        NavigationBarItem(
            selected = selectedItem == "statistics",
            onClick = onStatisticsClick,
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Statistics") },
            label = { Text("Stats") },
            colors = bottomBarColors()
        )

        NavigationBarItem(
            selected = selectedItem == "settings",
            onClick = onSettingsClick,
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            colors = bottomBarColors()
        )
    }
}

@Composable
private fun bottomBarColors(): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = Primary,
        selectedTextColor = Accent,
        indicatorColor = Accent,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
    )
}
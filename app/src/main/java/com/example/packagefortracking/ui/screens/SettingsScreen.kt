package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.AppPreferences
import com.example.packagefortracking.notification.NotificationHelper
import com.example.packagefortracking.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val preferences = remember { AppPreferences(context) }

    var notifications by remember { mutableStateOf(preferences.isNotificationsEnabled()) }
    var darkMode by remember { mutableStateOf(preferences.isDarkModeEnabled()) }
    var autoRefresh by remember { mutableStateOf(preferences.isAutoRefreshEnabled()) }
    var deliveryAlerts by remember { mutableStateOf(preferences.isDeliveryAlertsEnabled()) }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = AppSurface,
                    navigationIconContentColor = AppSurface
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {
            Text(
                text = "App preferences",
                color = TextDark,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Manage tracking, notifications and app information.",
                color = TextMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSectionTitle("Tracking preferences")

            SettingsCard {
                SettingsSwitchRow(
                    title = "Shipment updates",
                    subtitle = "Notify when package status changes.",
                    icon = Icons.Default.Notifications,
                    checked = notifications,
                    onCheckedChange = {
                        notifications = it
                        preferences.setNotificationsEnabled(it)

                        NotificationHelper.showPackageNotification(
                            context = context,
                            title = if (it) "Notifications enabled" else "Notifications disabled",
                            message = if (it)
                                "You will receive package tracking updates."
                            else
                                "You will no longer receive package tracking updates."
                        )
                    }
                )

                SettingsSwitchRow(
                    title = "Auto refresh",
                    subtitle = "Refresh saved packages automatically.",
                    icon = Icons.Default.Sync,
                    checked = autoRefresh,
                    onCheckedChange = {
                        autoRefresh = it
                        preferences.setAutoRefreshEnabled(it)
                    }
                )

                SettingsSwitchRow(
                    title = "Delivery alerts",
                    subtitle = "Receive alerts for upcoming deliveries.",
                    icon = Icons.Default.Timer,
                    checked = deliveryAlerts,
                    onCheckedChange = {
                        deliveryAlerts = it
                        preferences.setDeliveryAlertsEnabled(it)

                        NotificationHelper.showPackageNotification(
                            context = context,
                            title = if (it) "Delivery alerts enabled" else "Delivery alerts disabled",
                            message = if (it)
                                "You will receive alerts for upcoming deliveries."
                            else
                                "Delivery alert notifications are turned off."
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSectionTitle("Appearance")

            SettingsCard {
                SettingsSwitchRow(
                    title = "Dark mode",
                    subtitle = "Dark interface support will be available in the next version.",
                    icon = Icons.Default.DarkMode,
                    checked = darkMode,
                    onCheckedChange = {
                        darkMode = it
                        preferences.setDarkModeEnabled(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSectionTitle("Project information")

            InfoCard(
                icon = Icons.Default.WorkspacePremium,
                title = "Bachelor thesis project",
                subtitle = "Mobile Package Tracking Application for Postal Services in Bosnia and Herzegovina"
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoCard(
                icon = Icons.Default.PrivacyTip,
                title = "Offline-first design",
                subtitle = "Packages are stored locally using Room Database."
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoCard(
                icon = Icons.Default.Info,
                title = "Package Tracker",
                subtitle = "Version 1.0 • Developed by Ada • International Burch University"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsSectionTitle(
    title: String
) {
    Text(
        text = title,
        color = TextDark,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun SettingsCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content
        )
    }
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Accent.copy(alpha = 0.18f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = subtitle,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppSurface,
                checkedTrackColor = Primary,
                uncheckedThumbColor = AppSurface,
                uncheckedTrackColor = TextMuted.copy(alpha = 0.35f)
            )
        )
    }
}

@Composable
private fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(Accent.copy(alpha = 0.18f), RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    color = TextDark,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    color = TextMuted,
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}
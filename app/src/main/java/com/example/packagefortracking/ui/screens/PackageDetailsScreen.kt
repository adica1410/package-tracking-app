package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.dummyPackages
import com.example.packagefortracking.ui.components.StatusChip
import com.example.packagefortracking.ui.components.TimelineItem
import com.example.packagefortracking.ui.components.providerName
import com.example.packagefortracking.ui.theme.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.filled.Edit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailsScreen(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val packageItem = dummyPackages.first()

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Package Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = AppSurface,
                    navigationIconContentColor = AppSurface,
                    actionIconContentColor = AppSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues)
                .padding(18.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Accent.copy(alpha = 0.18f), RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.LocalShipping,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(34.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = packageItem.name,
                                color = TextDark,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = providerName(packageItem.postalService),
                                color = TextMuted,
                                fontSize = 14.sp
                            )
                        }

                        StatusChip(packageItem.status)
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    LinearProgressIndicator(
                        progress = { 0.65f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Accent,
                        trackColor = DividerLight
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    DetailRow("Tracking number", packageItem.trackingNumber, Icons.Default.QrCode2)
                    DetailRow("Last update", packageItem.lastUpdate, Icons.Default.CalendarMonth)
                    DetailRow("Estimated delivery", packageItem.estimatedDelivery, Icons.Default.LocalShipping)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Tracking timeline",
                color = TextDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    TimelineItem(
                        title = "Package registered",
                        description = "Shipment information received.",
                        date = "10 Mar 2026, 09:20",
                        completed = true
                    )

                    TimelineItem(
                        title = "Accepted by postal service",
                        description = "Package accepted at sorting center.",
                        date = "11 Mar 2026, 13:45",
                        completed = true
                    )

                    TimelineItem(
                        title = "In transit",
                        description = "Package is moving to destination city.",
                        date = "13 Mar 2026, 18:10",
                        completed = true
                    )

                    TimelineItem(
                        title = "Out for delivery",
                        description = "Courier will deliver the package soon.",
                        date = "Expected soon",
                        completed = false
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = label,
                color = TextMuted,
                fontSize = 12.sp
            )

            Text(
                text = value,
                color = TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
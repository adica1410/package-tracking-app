package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.data.PackageItem
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.PostalService
import com.example.packagefortracking.tracking.TrackingEvent
import com.example.packagefortracking.ui.components.StatusChip
import com.example.packagefortracking.ui.components.TimelineItem
import com.example.packagefortracking.ui.components.providerName
import com.example.packagefortracking.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailsScreen(
    packageEntity: PackageEntity?,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onArchiveClick: (PackageEntity) -> Unit,
    onRefreshClick: (PackageEntity) -> Unit
) {
    if (packageEntity == null) {
        MissingPackageScreen(onBackClick = onBackClick)
        return
    }

    val packageItem = packageEntity.toPackageItem()
    val progress = progressForStatus(packageItem.status)
    val trackingEvents = parseTrackingEvents(packageEntity.trackingEvents)

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Package Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }

                    IconButton(onClick = { onRefreshClick(packageEntity) }) {
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
                .verticalScroll(rememberScrollState())
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
                                .background(
                                    Accent.copy(alpha = 0.18f),
                                    RoundedCornerShape(20.dp)
                                ),
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
                                fontWeight = FontWeight.Bold,
                                maxLines = 2
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
                        progress = { progress },
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
                    if (trackingEvents.isEmpty()) {
                        TimelineItem(
                            title = "Package registered",
                            description = "Shipment was added to the tracking list.",
                            date = packageItem.lastUpdate,
                            completed = true
                        )

                        TimelineItem(
                            title = "Accepted by postal service",
                            description = "Waiting for official postal tracking update.",
                            date = "Pending update",
                            completed = false
                        )

                        TimelineItem(
                            title = "In transit",
                            description = "Package is waiting for the first tracking refresh.",
                            date = "Pending",
                            completed = false
                        )

                        TimelineItem(
                            title = "Out for delivery",
                            description = "Courier delivery has not started yet.",
                            date = "Pending",
                            completed = false
                        )

                        TimelineItem(
                            title = "Delivered",
                            description = "Package has not been delivered yet.",
                            date = "Pending",
                            completed = false
                        )
                    } else {
                        trackingEvents.forEach { event ->
                            TimelineItem(
                                title = event.title,
                                description = event.description,
                                date = event.date,
                                completed = event.completed
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedButton(
                onClick = {
                    onArchiveClick(packageEntity.copy(status = "ARCHIVED"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Primary
                )
            ) {
                Icon(Icons.Default.Archive, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Move to Archive", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MissingPackageScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Package Details", fontWeight = FontWeight.Bold) },
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
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Package not found",
                color = TextMuted,
                fontSize = 16.sp
            )
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

private fun PackageEntity.toPackageItem(): PackageItem {
    return PackageItem(
        id = id,
        name = name,
        trackingNumber = trackingNumber,
        postalService = postalServiceFromString(postalService),
        status = statusFromString(status),
        lastUpdate = lastUpdate,
        estimatedDelivery = estimatedDelivery,
        notificationsEnabled = notificationsEnabled
    )
}

private fun statusFromString(status: String): PackageStatus {
    return when (status) {
        "IN_TRANSIT" -> PackageStatus.IN_TRANSIT
        "OUT_FOR_DELIVERY" -> PackageStatus.OUT_FOR_DELIVERY
        "DELIVERED" -> PackageStatus.DELIVERED
        "ARCHIVED" -> PackageStatus.ARCHIVED
        else -> PackageStatus.IN_TRANSIT
    }
}

private fun postalServiceFromString(postalService: String): PostalService {
    return when (postalService) {
        "BH Post" -> PostalService.BH_POST
        "HP Mostar" -> PostalService.HP_MOSTAR
        "Pošta Srpske" -> PostalService.POSTA_SRPSKE
        else -> PostalService.BH_POST
    }
}

private fun progressForStatus(status: PackageStatus): Float {
    return when (status) {
        PackageStatus.IN_TRANSIT -> 0.45f
        PackageStatus.OUT_FOR_DELIVERY -> 0.75f
        PackageStatus.DELIVERED -> 1f
        PackageStatus.ARCHIVED -> 1f
    }
}

private fun parseTrackingEvents(events: String): List<TrackingEvent> {
    if (events.isBlank()) return emptyList()

    return events.split("||").mapNotNull { rawEvent ->
        val parts = rawEvent.split("###")

        if (parts.size != 4) {
            null
        } else {
            TrackingEvent(
                title = parts[0],
                description = parts[1],
                date = parts[2],
                completed = parts[3].toBoolean()
            )
        }
    }
}
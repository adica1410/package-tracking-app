package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.data.PackageItem
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.PostalService
import com.example.packagefortracking.ui.components.PackageCard
import com.example.packagefortracking.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    packages: List<PackageEntity>,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Archive",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Archive,
                        contentDescription = null,
                        tint = Primary
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "${packages.size} archived packages",
                            color = TextDark,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Delivered or archived shipments appear here.",
                            color = TextMuted
                        )
                    }
                }
            }

            if (packages.isEmpty()) {
                EmptyArchiveState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(packages) { packageEntity ->
                        Box(
                            modifier = Modifier.padding(
                                horizontal = 18.dp,
                                vertical = 8.dp
                            )
                        ) {
                            PackageCard(
                                packageItem = packageEntity.toPackageItem(),
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyArchiveState() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 18.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Archive is empty",
                color = TextDark,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Archived and delivered packages will appear here.",
                color = TextMuted
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
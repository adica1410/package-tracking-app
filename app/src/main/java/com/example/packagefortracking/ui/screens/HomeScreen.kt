package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.data.PackageItem
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.PostalService
import com.example.packagefortracking.ui.components.AppBottomBar
import com.example.packagefortracking.ui.components.PackageCard
import com.example.packagefortracking.ui.theme.*

@Composable
fun HomeScreen(
    packages: List<PackageEntity>,
    onAddPackageClick: () -> Unit,
    onPackageClick: (PackageEntity) -> Unit,
    onArchiveClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredPackages = packages
        .filter {
            val status = statusFromString(it.status)

            when (selectedFilter) {
                "In Transit" -> status == PackageStatus.IN_TRANSIT || status == PackageStatus.OUT_FOR_DELIVERY
                "Delivered" -> status == PackageStatus.DELIVERED
                else -> status != PackageStatus.ARCHIVED
            }
        }
        .filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.trackingNumber.contains(searchQuery, ignoreCase = true)
        }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            AppBottomBar(
                selectedItem = "home",
                onHomeClick = {},
                onArchiveClick = onArchiveClick,
                onStatisticsClick = onStatisticsClick,
                onSettingsClick = onSettingsClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPackageClick,
                containerColor = Accent,
                contentColor = Primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add package"
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                HomeHeader()
            }

            item {
                SearchBox(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it }
                )
            }

            item {
                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )
            }

            item {
                HomeSummary(packages = packages)
            }

            item {
                SectionTitle(totalPackages = filteredPackages.size)
            }

            if (filteredPackages.isEmpty()) {
                item {
                    EmptyPackagesState(
                        onAddPackageClick = onAddPackageClick
                    )
                }
            } else {
                items(filteredPackages) { packageEntity ->
                    Box(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        PackageCard(
                            packageItem = packageEntity.toPackageItem(),
                            onClick = {
                                onPackageClick(packageEntity)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(245.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Primary, PrimaryLight)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = 230.dp, y = 60.dp)
                .clip(CircleShape)
                .background(AppSurface.copy(alpha = 0.08f))
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 46.dp)
        ) {
            Text(
                text = "Welcome to Package Tracker",
                color = AppSurface.copy(alpha = 0.85f),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Track your\npackages easily",
                color = AppSurface,
                fontSize = 34.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "All postal shipments in one modern mobile application.",
                color = AppSurface.copy(alpha = 0.85f),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun SearchBox(
    searchQuery: String,
    onSearchChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .offset(y = (-28).dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search by name or tracking number...",
                    color = TextMuted,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TextMuted
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppSurface,
                unfocusedBorderColor = AppSurface,
                focusedContainerColor = AppSurface,
                unfocusedContainerColor = AppSurface,
                cursorColor = Primary
            ),
            shape = RoundedCornerShape(18.dp)
        )
    }
}

@Composable
private fun FilterSection(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("All", "In Transit", "Delivered")

    Row(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .offset(y = (-12).dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        filters.forEach { filter ->
            FilterButton(
                text = filter,
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
private fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Primary else AppSurface,
            contentColor = if (selected) AppSurface else TextMuted
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (selected) 3.dp else 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SectionTitle(
    totalPackages: Int
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .padding(top = 2.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "My packages",
            color = TextDark,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "$totalPackages total",
            color = TextMuted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun HomeSummary(
    packages: List<PackageEntity>
) {
    val total = packages.count { statusFromString(it.status) != PackageStatus.ARCHIVED }

    val inTransit = packages.count {
        val status = statusFromString(it.status)
        status == PackageStatus.IN_TRANSIT || status == PackageStatus.OUT_FOR_DELIVERY
    }

    val delivered = packages.count {
        statusFromString(it.status) == PackageStatus.DELIVERED
    }

    Text(
        text = "$total packages • $inTransit in transit • $delivered delivered",
        color = TextMuted,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .offset(y = (-6).dp)
    )
}

@Composable
private fun EmptyPackagesState(
    onAddPackageClick: () -> Unit
) {
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
                text = "No packages found",
                color = TextDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Try changing your search or add a new package.",
                color = TextMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onAddPackageClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Primary
                )
            ) {
                Text("Add Package", fontWeight = FontWeight.Bold)
            }
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
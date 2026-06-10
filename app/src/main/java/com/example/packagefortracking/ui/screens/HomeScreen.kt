package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.dummyPackages
import com.example.packagefortracking.ui.components.AppBottomBar
import com.example.packagefortracking.ui.components.PackageCard
import com.example.packagefortracking.ui.theme.*

@Composable
fun HomeScreen(
    onAddPackageClick: () -> Unit,
    onPackageClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredPackages = when (selectedFilter) {
        "In Transit" -> dummyPackages.filter { it.status == PackageStatus.IN_TRANSIT || it.status == PackageStatus.OUT_FOR_DELIVERY }
        "Delivered" -> dummyPackages.filter { it.status == PackageStatus.DELIVERED }
        else -> dummyPackages.filter { it.status != PackageStatus.ARCHIVED }
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
                SearchBox()
            }

            item {
                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )
            }

            item {
                SectionTitle()
            }

            items(filteredPackages) { packageItem ->
                Box(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    PackageCard(
                        packageItem = packageItem,
                        onClick = onPackageClick
                    )
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
                text = "Welcome back, Ada",
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
private fun SearchBox() {
    Card(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .offset(y = (-28).dp)
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = TextMuted
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Search by tracking number...",
                color = TextMuted,
                fontSize = 14.sp
            )
        }
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
private fun SectionTitle() {
    Row(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .padding(top = 2.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Active packages",
            color = TextDark,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${dummyPackages.size} total",
            color = TextMuted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
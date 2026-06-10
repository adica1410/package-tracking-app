package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.dummyPackages
import com.example.packagefortracking.ui.components.StatCard
import com.example.packagefortracking.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onBackClick: () -> Unit
) {
    val total = dummyPackages.size
    val delivered = dummyPackages.count { it.status == PackageStatus.DELIVERED }
    val inTransit = dummyPackages.count {
        it.status == PackageStatus.IN_TRANSIT || it.status == PackageStatus.OUT_FOR_DELIVERY
    }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Statistics",
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .padding(18.dp)
        ) {
            Text(
                text = "Package overview",
                color = TextDark,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Track performance and delivery progress.",
                color = TextMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    StatCard(
                        title = "Total packages",
                        value = total.toString(),
                        icon = Icons.Default.Inventory2
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    StatCard(
                        title = "Delivered",
                        value = delivered.toString(),
                        icon = Icons.Default.CheckCircle
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    StatCard(
                        title = "In transit",
                        value = inTransit.toString(),
                        icon = Icons.Default.LocalShipping
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    StatCard(
                        title = "Avg. delivery",
                        value = "5d",
                        icon = Icons.Default.Schedule
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Delivery progress",
                        color = TextDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    ProgressRow("Delivered", delivered, total, Success)
                    ProgressRow("In Transit", inTransit, total, Warning)
                    ProgressRow("Other", total - delivered - inTransit, total, ArchivedGray)
                }
            }
        }
    }
}

@Composable
private fun ProgressRow(
    label: String,
    count: Int,
    total: Int,
    color: androidx.compose.ui.graphics.Color
) {
    val progress = if (total == 0) 0f else count.toFloat() / total.toFloat()

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row {
            Text(
                text = label,
                color = TextDark,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$count/$total",
                color = TextMuted
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = DividerLight
        )
    }
}
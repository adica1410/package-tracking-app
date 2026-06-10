package com.example.packagefortracking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.PackageItem
import com.example.packagefortracking.data.PostalService
import com.example.packagefortracking.ui.theme.*

@Composable
fun PackageCard(
    packageItem: PackageItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Accent.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = packageItem.name,
                        color = TextDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = providerName(packageItem.postalService),
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                }

                StatusChip(status = packageItem.status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = DividerLight)

            Spacer(modifier = Modifier.height(14.dp))

            InfoLine(
                icon = Icons.Default.QrCode2,
                label = "Tracking",
                value = packageItem.trackingNumber
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoLine(
                icon = Icons.Default.CalendarMonth,
                label = "Last update",
                value = packageItem.lastUpdate
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View details",
                    color = Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoLine(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$label: ",
            color = TextMuted,
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = TextDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun providerName(service: PostalService): String {
    return when (service) {
        PostalService.BH_POST -> "BH Post"
        PostalService.HP_MOSTAR -> "HP Mostar"
        PostalService.POSTA_SRPSKE -> "Pošta Srpske"
    }
}
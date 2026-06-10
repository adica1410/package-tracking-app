package com.example.packagefortracking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.ui.theme.ArchivedGray
import com.example.packagefortracking.ui.theme.Success
import com.example.packagefortracking.ui.theme.Warning

@Composable
fun StatusChip(
    status: PackageStatus
) {
    val color = when (status) {
        PackageStatus.IN_TRANSIT -> Warning
        PackageStatus.OUT_FOR_DELIVERY -> Color(0xFF3B82F6)
        PackageStatus.DELIVERED -> Success
        PackageStatus.ARCHIVED -> ArchivedGray
    }

    val text = when (status) {
        PackageStatus.IN_TRANSIT -> "In Transit"
        PackageStatus.OUT_FOR_DELIVERY -> "Out For Delivery"
        PackageStatus.DELIVERED -> "Delivered"
        PackageStatus.ARCHIVED -> "Archived"
    }

    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .background(
                color.copy(alpha = 0.15f),
                RoundedCornerShape(50)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    )
}
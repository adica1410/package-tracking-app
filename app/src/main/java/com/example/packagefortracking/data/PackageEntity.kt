package com.example.packagefortracking.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packages")
data class PackageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val trackingNumber: String,
    val postalService: String,
    val status: String,
    val lastUpdate: String,
    val estimatedDelivery: String,
    val notificationsEnabled: Boolean
)
package com.example.packagefortracking.data

enum class PackageStatus {
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    ARCHIVED
}

enum class PostalService {
    BH_POST,
    HP_MOSTAR,
    POSTA_SRPSKE
}

data class PackageItem(
    val id: Int,
    val name: String,
    val trackingNumber: String,
    val postalService: PostalService,
    val status: PackageStatus,
    val lastUpdate: String,
    val estimatedDelivery: String,
    val notificationsEnabled: Boolean = true
)
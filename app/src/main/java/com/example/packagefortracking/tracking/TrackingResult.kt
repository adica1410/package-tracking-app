package com.example.packagefortracking.tracking

data class TrackingResult(
    val status: String,
    val lastUpdate: String,
    val estimatedDelivery: String,
    val events: List<TrackingEvent>
)
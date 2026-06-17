package com.example.packagefortracking.tracking

interface TrackingProvider {

    suspend fun track(
        trackingNumber: String
    ): TrackingResult
}
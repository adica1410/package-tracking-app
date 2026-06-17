package com.example.packagefortracking.tracking

class BhPostTrackingProvider : TrackingProvider {

    override suspend fun track(
        trackingNumber: String
    ): TrackingResult {

        return TrackingResult(
            status = "IN_TRANSIT",
            lastUpdate = "15 Jun 2026",
            estimatedDelivery = "18 Jun 2026",
            events = listOf(
                TrackingEvent(
                    title = "Shipment received",
                    description = "Package received by BH Post",
                    date = "15 Jun 2026",
                    completed = true
                ),
                TrackingEvent(
                    title = "In transit",
                    description = "Package is travelling",
                    date = "16 Jun 2026",
                    completed = true
                )
            )
        )
    }
}
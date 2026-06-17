package com.example.packagefortracking.tracking

class PostaSrpskeTrackingProvider : TrackingProvider {

    override suspend fun track(
        trackingNumber: String
    ): TrackingResult {

        return TrackingResult(
            status = "IN_TRANSIT",
            lastUpdate = "16 Jun 2026",
            estimatedDelivery = "19 Jun 2026",
            events = listOf(
                TrackingEvent(
                    title = "Shipment registered",
                    description = "Package registered by Pošta Srpske",
                    date = "15 Jun 2026",
                    completed = true
                ),
                TrackingEvent(
                    title = "Processing",
                    description = "Package is being processed",
                    date = "16 Jun 2026",
                    completed = true
                ),
                TrackingEvent(
                    title = "In transit",
                    description = "Package is on the way to destination",
                    date = "16 Jun 2026",
                    completed = true
                )
            )
        )
    }
}
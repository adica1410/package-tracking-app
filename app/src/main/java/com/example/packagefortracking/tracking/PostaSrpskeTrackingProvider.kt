package com.example.packagefortracking.tracking

class PostaSrpskeTrackingProvider : TrackingProvider {

    override suspend fun track(trackingNumber: String): TrackingResult {
        return TrackingResult(
            status = "DELIVERED",
            lastUpdate = "Shipment delivered successfully by Pošta Srpske.",
            estimatedDelivery = "Delivered",
            events = listOf(
                TrackingEvent(
                    title = "Shipment Created",
                    description = "The shipment was created in the Pošta Srpske tracking system.",
                    date = "16 Jun 2026, 08:30",
                    completed = true
                ),
                TrackingEvent(
                    title = "Accepted by Pošta Srpske",
                    description = "The package was accepted at the postal branch.",
                    date = "16 Jun 2026, 13:10",
                    completed = true
                ),
                TrackingEvent(
                    title = "Dispatched",
                    description = "The shipment was dispatched from the regional processing center.",
                    date = "17 Jun 2026, 11:45",
                    completed = true
                ),
                TrackingEvent(
                    title = "Out for Delivery",
                    description = "The shipment was assigned to the local delivery courier.",
                    date = "18 Jun 2026, 09:00",
                    completed = true
                ),
                TrackingEvent(
                    title = "Delivered",
                    description = "The package was delivered to the recipient.",
                    date = "18 Jun 2026, 14:25",
                    completed = true
                )
            )
        )
    }
}
package com.example.packagefortracking.tracking

class HpMostarTrackingProvider : TrackingProvider {

    override suspend fun track(
        trackingNumber: String
    ): TrackingResult {

        return TrackingResult(
            status = "OUT_FOR_DELIVERY",
            lastUpdate = "16 Jun 2026",
            estimatedDelivery = "17 Jun 2026",
            events = listOf(
                TrackingEvent(
                    title = "Shipment accepted",
                    description = "Package accepted by HP Mostar",
                    date = "14 Jun 2026",
                    completed = true
                ),
                TrackingEvent(
                    title = "Sorting center",
                    description = "Package processed in sorting center",
                    date = "15 Jun 2026",
                    completed = true
                ),
                TrackingEvent(
                    title = "Out for delivery",
                    description = "Courier is delivering the package",
                    date = "16 Jun 2026",
                    completed = true
                )
            )
        )
    }
}
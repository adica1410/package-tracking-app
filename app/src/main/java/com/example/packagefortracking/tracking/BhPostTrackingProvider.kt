package com.example.packagefortracking.tracking

class BhPostTrackingProvider : TrackingProvider {

    override suspend fun track(trackingNumber: String): TrackingResult {
        return TrackingResult(
            status = "OUT_FOR_DELIVERY",
            lastUpdate = "BH Post courier is delivering the package.",
            estimatedDelivery = "Today",
            events = listOf(
                TrackingEvent(
                    title = "Shipment Registered",
                    description = "Shipment information was created in the BH Post system.",
                    date = "18 Jun 2026, 09:10",
                    completed = true
                ),
                TrackingEvent(
                    title = "Accepted by BH Post",
                    description = "The package was accepted at the BH Post postal office.",
                    date = "18 Jun 2026, 12:45",
                    completed = true
                ),
                TrackingEvent(
                    title = "In Transit",
                    description = "The package is moving through the BH Post sorting center.",
                    date = "19 Jun 2026, 08:30",
                    completed = true
                ),
                TrackingEvent(
                    title = "Out for Delivery",
                    description = "The package is currently with the BH Post courier.",
                    date = "Today, 09:20",
                    completed = true
                ),
                TrackingEvent(
                    title = "Delivered",
                    description = "The package has not been delivered yet.",
                    date = "Pending",
                    completed = false
                )
            )
        )
    }
}
package com.example.packagefortracking.tracking

class HpMostarTrackingProvider : TrackingProvider {

    override suspend fun track(trackingNumber: String): TrackingResult {
        return TrackingResult(
            status = "IN_TRANSIT",
            lastUpdate = "Shipment processed at HP Mostar distribution center.",
            estimatedDelivery = "2 days",
            events = listOf(
                TrackingEvent(
                    title = "Order Received",
                    description = "Tracking request was registered in the HP Mostar system.",
                    date = "17 Jun 2026, 10:15",
                    completed = true
                ),
                TrackingEvent(
                    title = "Accepted by Postal Service",
                    description = "The shipment was accepted by HP Mostar.",
                    date = "17 Jun 2026, 15:40",
                    completed = true
                ),
                TrackingEvent(
                    title = "Processed at Distribution Center",
                    description = "The package was processed at the HP Mostar distribution center.",
                    date = "18 Jun 2026, 09:25",
                    completed = true
                ),
                TrackingEvent(
                    title = "In Transit to Destination",
                    description = "The package is currently being transported to the destination city.",
                    date = "19 Jun 2026, 07:50",
                    completed = true
                ),
                TrackingEvent(
                    title = "Out for Delivery",
                    description = "The package has not yet been assigned to a courier.",
                    date = "Pending",
                    completed = false
                )
            )
        )
    }
}